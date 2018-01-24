package com.wayoup.server.core.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayoup.server.core.data.entity.UserFacebook;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.FacebookAccountRepo;
import com.wayoup.server.core.enums.ExtAccountType;
import com.wayoup.server.core.service.FacebookService;
import com.wayoup.server.core.service.dto.FacebookAccountDTO;
import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.dto.SessionDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import com.wayoup.server.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wayoup.server.core.enums.ErrorType.*;

/**
 * Created by Marco Romagnolo on 12/05/2015.
 */
/**
 * json={"id":"10206879432853637","first_name":"Marcolino","timezone":2,"email":"romagnolo.marco@gmail.com",
 * "verified":true,"name":"Marcolino Loco Loco","locale":"it_IT",
 * "link":"https://www.facebook.com/app_scoped_user_id/10206879432853637/","last_name":"Loco Loco","gender":"male",
 * "updated_time":"2015-04-22T17:53:43+0000"}
 */
@Service("facebookService")
public class FacebookServiceImpl extends AbstractService implements FacebookService {

    private static final Logger logger = Logger.getLogger(FacebookServiceImpl.class.getName());

    @Autowired
    private FacebookAccountRepo<UserFacebook> facebookRepo;

    @Value("${facebook.id}")
    private String appId;

    @Value("${facebook.secret}")
    private String appSecret;

    @Value("${facebook.redirectUri}")
    private String redirectUri;

    @Override
    public String login() throws GenericException {
        try {
            return  "http://www.facebook.com/dialog/oauth?" + "client_id=" + appId
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_UNSUPPORTED_ENCODING);
        }
    }

    @Override
    public LoginDTO callback(String code, String remoteAddress, String headers) throws GenericException, InternalException {
        Map<String, String> profile;
        try {
            String accessUrl =  "https://graph.facebook.com/oauth/access_token?" + "client_id=" + appId + "&redirect_uri="
                    + URLEncoder.encode(redirectUri, "UTF-8") + "&client_secret=" + appSecret + "&code=" + code;
            String accessData = getData(accessUrl);
            int index = accessData.indexOf("access_token=");
            String fbToken = index!=-1 ? accessData.substring(index) : "";
            String profileUrl = "https://graph.facebook.com/me?" + fbToken;
            String profileData = getData(profileUrl);
            profile = getObject(profileData);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_UNSUPPORTED_ENCODING);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_FACEBOOK_ERROR);
        }
        // New Session
        if (profile==null) throw new GenericException(ERROR_FACEBOOK_ERROR);
        try {
            UserFacebook user = facebookRepo.findByExtId(profile.get("id"));
            Date updatedTime = DateUtil.parseDateTime(profile.get("updated_time"));
            Date now = new Date();
            //Create User
            if (user == null) {
                user = new UserFacebook();
                user.setExtId(profile.get("id"));
                user.setExtType(ExtAccountType.FACEBOOK.toString());
                user.setFirstName(profile.get("first_name"));
                user.setLastName(profile.get("last_name"));
                user.setEmail(profile.get("email"));
                user.setCreateDate(now);
                user = facebookRepo.save(user);
            }
            // Update User
            if (user.getModifyDate() != null && updatedTime.after(user.getModifyDate())) {
                user.setFirstName(profile.get("first_name"));
                user.setLastName(profile.get("last_name"));
                user.setEmail(profile.get("email"));
                user.setModifyDate(updatedTime);
                facebookRepo.save(user);
            }
            SessionDTO sessionDTO = createSession(user, false, remoteAddress, headers);
            return new LoginDTO(sessionDTO, new FacebookAccountDTO(user));
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            facebookRepo.close();
        }
    }

    @Override
    public void logout(String extId, String token, String remoteAddress, String headers) throws GenericException, AuthorizationException, InternalException {
        try {
            UserFacebook user = facebookRepo.findByExtId(extId);
            destroySession(user, token, remoteAddress, headers);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
           facebookRepo.close();
        }
    }

    private String getData(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine).append("\n");
        }
        in.close();
        return sb.toString();
    }

    private Map<String, String> getObject(String data) throws IOException {
        return new ObjectMapper().readValue(data, new TypeReference<HashMap<String, String>>() {});
    }

}
