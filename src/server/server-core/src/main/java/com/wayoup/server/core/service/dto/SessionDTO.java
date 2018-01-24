package com.wayoup.server.core.service.dto;

import java.util.Date;

/**
 * Created by Marco on 16/07/2015.
 */
public class SessionDTO {

    private String userId;
    private String token;
    private boolean expiry;
    private Date createDate;

    public SessionDTO(String userId, String token, boolean expiry, Date createDate) {
        this.userId = userId;
        this.token = token;
        this.expiry = expiry;
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
