package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 24/03/2015.
 */
public class Session extends AbstractEntity {

    private User user;
    private String token;
    private boolean expiry;
    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
