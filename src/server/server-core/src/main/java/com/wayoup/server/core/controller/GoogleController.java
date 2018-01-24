package com.wayoup.server.core.controller;

import com.wayoup.server.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco Romagnolo on 04/05/2015.
 */

/**
 *
 * {"token":"2755010335-OEkcXUIxiENMVie1F9NucbaEdvN6di2npjC2ceF",
 * "tokenSecret":"xY88FLdvnxGvNNUs2sIp7audYBYAfA2IbUfldb4YtIJGJ","screenName":"mromagnolo85","userId":2755010335}
 * */
@RestController
public class GoogleController {

    private static Map<String, RequestToken> tokens = new HashMap<>();

    @Autowired
    private AccountService accountService;

    @Value("${google.id}")
    private String appKey;

    @Value("${google.secret}")
    private String appSecret;

    @Value("${google.redirectUri}")
    private String redirectUri;

    @RequestMapping(value = "/google/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(value = "/google/callback", method = RequestMethod.GET)
    public ResponseEntity callback(@RequestParam("oauth_token") String token, @RequestParam("oauth_verifier") String verifier) {
        return null;
    }

}
