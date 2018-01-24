package com.wayoup.server.core.service.dto;

import com.wayoup.server.core.data.entity.UserFacebook;

/**
 * Created by Marco on 07/06/2015.
 */
public class FacebookAccountDTO extends ExtAccountDTO {

    private final String firstName;
    private final String lastName;
    private final String link;
    private final boolean verified;
    private final String email;
    private final String gender;
    private final String timezone;
    private final String locale;

    public FacebookAccountDTO(UserFacebook user) {
        super(user);
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.link = user.getLink();
        this.verified = user.isVerified();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.timezone = user.getTimezone();
        this.locale = user.getLocale();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLink() {
        return link;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLocale() {
        return locale;
    }
}
