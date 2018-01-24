package com.wayoup.server.core.service.dto;

import com.wayoup.server.core.data.entity.UserTwitter;

/**
 * Created by Marco on 07/06/2015.
 */
public class TwitterAccountDTO extends ExtAccountDTO {

    private final String screeName;
    private final String name;

    public TwitterAccountDTO(UserTwitter user) {
        super(user);
        this.screeName = user.getScreenName();
        this.name = user.getName();
    }

    public String getScreeName() {
        return screeName;
    }

    public String getName() {
        return name;
    }
}
