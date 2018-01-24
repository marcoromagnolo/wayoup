package com.wayoup.server.core.service.impl;

import com.wayoup.server.core.data.entity.Session;
import com.wayoup.server.core.data.entity.SessionHistory;
import com.wayoup.server.core.data.entity.User;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.SessionHistoryRepo;
import com.wayoup.server.core.data.repository.SessionRepo;
import com.wayoup.server.core.enums.ErrorType;
import com.wayoup.server.core.service.dto.SessionDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wayoup.server.core.enums.ErrorType.ERROR_INVALID_SESSION;
import static com.wayoup.server.core.enums.ErrorType.ERROR_SESSION_EXPIRED;

/**
 * Created by Marco Romagnolo on 17/05/2015.
 */
public abstract class AbstractService {

    private static final Logger logger = Logger.getLogger(AbstractService.class.getName());

    private static Map<String, SessionDTO> sessions;

    @Autowired
    private SessionRepo<Session> sessionRepo;

    @Autowired
    private SessionHistoryRepo<SessionHistory> sessionHistoryRepo;

    @Value("${service.auth.multisession}")
    private boolean multiSession;

    @Value("${auth.session.maxInactiveInterval}")
    private int maxInactiveInterval;

    @PostConstruct
    private void init() throws GenericException, DbException {
        if (sessions==null) {
            sessions = new HashMap<>();
            try {
                List<Object> persistentSessions = sessionRepo.findAll();
                for (Object object : persistentSessions) {
                    Session session = sessionRepo.toEntity(object);
                    sessions.put(session.getToken(), convert(session));
                }
            } catch (DataException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                throw new DbException();
            } finally {
                sessionRepo.close();
            }
        }
    }

    protected SessionDTO getSession(String token) throws AuthorizationException {
        if (token==null || !sessions.containsKey(token)) {
            throw new AuthorizationException(ERROR_INVALID_SESSION);
        }
        SessionDTO session = sessions.get(token);
        long now = new Date().getTime();
        long timeLimit = maxInactiveInterval + session.getCreateDate().getTime();
        if (session.isExpiry() && maxInactiveInterval>=0 && now>timeLimit) {
            throw new AuthorizationException(ERROR_SESSION_EXPIRED);
        }
        return session;
    }

    protected SessionDTO createSession(User user, boolean expiry, String address, String headers) throws GenericException, DataException {
        if (!multiSession) {
            if (sessionRepo.findByUser(user).isEmpty()) {
                throw new GenericException(ErrorType.ERROR_ALREADY_OPEN_SESSION);
            }
        }
        String token = generateToken();
        Date now = new Date();
        Session session = new Session();
        session.setToken(token);
        session.setCreateDate(now);
        session.setUser(user);
        session.setExpiry(expiry);
        sessionRepo.save(session);
        historyLogin(user, token, expiry, now, address, headers);
        SessionDTO sessionDTO = convert(session);
        sessions.put(session.getToken(), sessionDTO);
        return sessionDTO;
    }

    protected void destroySession(User user, String token, String remoteAddress, String headers) throws AuthorizationException, DataException, GenericException {
        SessionDTO session = getSession(token);
        if (session.getUserId().equals(user.getId())) {
            historyLogout(user, token, new Date(), remoteAddress, headers);
            sessions.remove(token);
            sessionRepo.deleteByToken(token);
        } else {
            throw new GenericException(ErrorType.ERROR_USER_INVALID_SESSION);
        }
    }

    private String generateToken() {
        String uniqueKey;
        do {
            uniqueKey = UUID.randomUUID().toString();
        } while (sessions.get(uniqueKey)!=null);
        return uniqueKey;
    }

    private void historyLogin(User user, String token, boolean expiry, Date date, String address, String headers) throws DataException {
        SessionHistory sessionHistory = new SessionHistory();
        sessionHistory.setUser(user);
        sessionHistory.setToken(token);
        sessionHistory.setExpiry(expiry);
        sessionHistory.setLoginDate(date);
        sessionHistory.setHeaders(headers);
        sessionHistory.setAddress(address);
        sessionHistoryRepo.save(sessionHistory);
    }

    private void historyLogout(User user, String token, Date date, String address, String headers) throws DataException {
        SessionHistory sessionHistory = new SessionHistory();
        sessionHistory.setToken(token);
        sessionHistory.setExpiry(false);
        sessionHistory.setLogoutDate(date);
        sessionHistory.setHeaders(headers);
        sessionHistory.setAddress(address);
        sessionHistoryRepo.save(sessionHistory);
    }

    private SessionDTO convert(Session session) {
        return new SessionDTO(session.getUser().getId(), session.getToken(), session.isExpiry(), session.getCreateDate());
    }

}
