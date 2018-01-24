package com.wayoup.server.core.service.impl;

import com.wayoup.server.core.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco on 23/06/2015.
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Value("${name}")
    private String name;

    @Value("${description}")
    private String description;

    @Value("${version}")
    private String version;

    @Override
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("name", name);
        config.put("version", version);
        Map<String, Object> url = new HashMap<>();
        config.put("url", url);
        url.put("login", "/login");
        url.put("logout", "/logout");
        url.put("register", "/register");
        url.put("confirm", "/confirm");
        url.put("recovery", "/recovery");
        url.put("reset", "/reset");
        url.put("facebookLogin", "/facebook/login");
        url.put("facebookLogout", "/facebook/logout");
        url.put("twitterLogin", "/twitter/login");
        url.put("twitterLogout", "/twitter/logout");
        return config;
    }

}
