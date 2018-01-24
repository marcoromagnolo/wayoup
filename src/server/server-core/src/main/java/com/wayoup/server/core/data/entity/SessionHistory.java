package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 24/03/2015.
 */
public class SessionHistory extends AbstractEntity {

    private User user;
    private String token;
    private boolean expiry;
    private String address;
    private String headers;
    private Date loginDate;
    private Date logoutDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpiry() {
        return expiry;
    }

    public void setExpiry(boolean expiry) {
        this.expiry = expiry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }
}
