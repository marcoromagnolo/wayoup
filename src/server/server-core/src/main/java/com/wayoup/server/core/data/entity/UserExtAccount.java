package com.wayoup.server.core.data.entity;

import com.wayoup.server.core.enums.ExtAccountType;

/**
 * Created by Marco Romagnolo on 19/05/2015.
 */
public abstract class UserExtAccount extends User {

    private String extId;
    private String extType;

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }
}
