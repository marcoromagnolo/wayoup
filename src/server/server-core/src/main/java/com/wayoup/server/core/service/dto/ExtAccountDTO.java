package com.wayoup.server.core.service.dto;

import com.wayoup.server.core.data.entity.UserExtAccount;
import com.wayoup.server.core.enums.ExtAccountType;

/**
 * Created by Marco on 07/06/2015.
 */
public class ExtAccountDTO extends UserDTO {

    private final String extId;
    private final ExtAccountType extAccountType;

    public ExtAccountDTO(UserExtAccount userExtAccount) {
        super(userExtAccount);
        this.extId = userExtAccount.getExtId();
        this.extAccountType = ExtAccountType.valueOf(userExtAccount.getExtType());
    }

    public String getExtId() {
        return extId;
    }

    public ExtAccountType getExtAccountType() {
        return extAccountType;
    }
}
