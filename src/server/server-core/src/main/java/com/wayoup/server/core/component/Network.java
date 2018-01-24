package com.wayoup.server.core.component;

import org.springframework.stereotype.Component;

/**
 * Created by Marco on 29/05/2015.
 */
@Component
public class Network {

    public Network(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword) {
        System.getProperties().put("http.proxyHost", proxyHost);
        System.getProperties().put("http.proxyPort", proxyPort);
        System.getProperties().put("http.proxyUser", proxyUsername);
        System.getProperties().put("http.proxyPassword", proxyPassword);
    }
}
