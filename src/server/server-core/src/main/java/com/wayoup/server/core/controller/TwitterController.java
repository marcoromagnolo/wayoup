package com.wayoup.server.core.controller;

import com.wayoup.server.core.controller.exception.HttpInternalServerErrorException;
import com.wayoup.server.core.controller.exception.HttpUnauthorizedException;
import com.wayoup.server.core.controller.exception.HttpForbiddenException;
import com.wayoup.server.core.controller.util.HttpUtil;
import com.wayoup.server.core.service.TwitterService;
import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marco Romagnolo on 04/05/2015.
 */
@RestController
public class TwitterController {

    private static final Logger logger = Logger.getLogger(TwitterController.class.getName());

    @Autowired
    private TwitterService twitterService;

    @RequestMapping(value = "/twitter/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String redirectUri = twitterService.login();
            response.sendRedirect(redirectUri);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/twitter/callback", method = RequestMethod.GET)
    public ResponseEntity callback(@RequestParam("oauth_token") String token,
                                   @RequestParam("oauth_verifier") String verifier,
                                   HttpServletRequest request) {
        try {
            LoginDTO login = twitterService.callback(token, verifier, request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(login, HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/twitter/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestParam("id") String id, @RequestParam("token") String token, HttpServletRequest request) {
        try {
            twitterService.logout(id, token, request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

}
