package com.wayoup.server.core.service.impl;

import com.wayoup.server.core.data.entity.UserTwitter;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.TwitterAccountRepo;
import com.wayoup.server.core.enums.ExtAccountType;
import com.wayoup.server.core.service.TwitterService;
import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.dto.SessionDTO;
import com.wayoup.server.core.service.dto.TwitterAccountDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wayoup.server.core.enums.ErrorType.ERROR_TWITTER_ERROR;

/**
 * Created by Marco Romagnolo on 12/05/2015.
 */

/**
 *
 * {"token":"2755010335-OEkcXUIxiENMVie1F9NucbaEdvN6di2npjC2ceF",
 * "tokenSecret":"xY88FLdvnxGvNNUs2sIp7audYBYAfA2IbUfldb4YtIJGJ","screenName":"mromagnolo85","userId":2755010335}
 * */
@Service("twitterService")
public class TwitterServiceImpl extends AbstractService implements TwitterService {

    private static final Logger logger = Logger.getLogger(TwitterServiceImpl.class.getName());

    @Autowired
    private TwitterAccountRepo<UserTwitter> twitterRepo;

    private static final Map<String, RequestToken> tokens = new HashMap<>();

    private static final Map<String, Twitter> instances = new HashMap<>();

    @Value("${twitter.key}")
    private String appKey;

    @Value("${twitter.secret}")
    private String appSecret;

    @Value("${twitter.redirectUri}")
    private String redirectUri;

    @Override
    public String login() throws GenericException {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(appKey, appSecret);
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken(redirectUri);
            tokens.put(requestToken.getToken(), requestToken);
            instances.put(requestToken.getToken(), twitter);
            return requestToken.getAuthenticationURL();
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_TWITTER_ERROR);
        }
    }

    @Override
    public LoginDTO callback(String token, String verifier, String remoteAddress, String headers) throws GenericException, InternalException {
        RequestToken requestToken = tokens.remove(token);
        Twitter twitter = instances.remove(requestToken.getToken());
        try {
            AccessToken profile = twitter.getOAuthAccessToken(requestToken, verifier);
            // New Session
            UserTwitter user = twitterRepo.findByExtId(String.valueOf(profile.getUserId()));
            Date now = new Date();
            //Get Twitter data
            twitter4j.User twitterUser = twitter.showUser(profile.getUserId());
            //Create User
            if (user==null) {
                user = new UserTwitter();
                user.setExtId(String.valueOf(profile.getUserId()));
                user.setExtType(ExtAccountType.TWITTER.toString());
                user.setCreateDate(now);
                user.setName(twitterUser.getName());
                user.setScreenName(twitterUser.getScreenName());
                user = twitterRepo.save(user);
            }
            // Update User
            boolean updated = false;
            if (!user.getScreenName().equals(twitterUser.getScreenName())) {
                user.setScreenName(twitterUser.getScreenName());
                updated = true;
            }
            if (!user.getName().equals(twitterUser.getName())) {
                user.setName(twitterUser.getName());
                updated = true;
            }
            if (updated) {
                user.setModifyDate(now);
                twitterRepo.save(user);
            }
            SessionDTO sessionDTO = createSession(user, false, remoteAddress, headers);
            return new LoginDTO(sessionDTO, new TwitterAccountDTO(user));
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_TWITTER_ERROR);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            twitterRepo.close();
        }
    }

    @Override
    public void logout(String extId, String token, String remoteAddress, String headers) throws GenericException, AuthorizationException, InternalException {
        try {
            UserTwitter user = twitterRepo.findByExtId(extId);
            destroySession(user, token, remoteAddress, headers);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            twitterRepo.close();
        }
    }

}
