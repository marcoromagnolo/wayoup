package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class UserBank extends AbstractEntity {

    private String ownerName;
    private Integer iban;
    private Integer swiftBic;
    private Date createDate;
    private Date modifyDate;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getIban() {
        return iban;
    }

    public void setIban(Integer iban) {
        this.iban = iban;
    }

    public Integer getSwiftBic() {
        return swiftBic;
    }

    public void setSwiftBic(Integer swiftBic) {
        this.swiftBic = swiftBic;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
