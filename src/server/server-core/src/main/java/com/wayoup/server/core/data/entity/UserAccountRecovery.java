package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class UserAccountRecovery extends AbstractEntity {

    private String tempPassword;
    private Date validDate;
    private Date createDate;

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
