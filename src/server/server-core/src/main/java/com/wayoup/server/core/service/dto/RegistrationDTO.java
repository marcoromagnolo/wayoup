package com.wayoup.server.core.service.dto;

import com.wayoup.server.core.data.entity.UserAccount;

import java.util.Date;

/**
 * Created by Marco on 07/06/2015.
 */
public class RegistrationDTO extends AccountDTO {

    private Date birthday;
    private Date createDate;

    public RegistrationDTO(UserAccount userAccount) {
        super(userAccount);
        this.birthday = userAccount.getBirthday();
        this.createDate = userAccount.getCreateDate();
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getCreateDate() {
        return createDate;
    }

}
