package com.wayoup.server.core.data.entity;

/**
 * Created by Marco on 06/06/2015.
 */
public class UserTwitter extends UserExtAccount {

    private String screenName;
    private String name;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
