package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public abstract class User extends AbstractEntity {

    private UserSettings settings;
    private UserBank bank;
    private UserBill bill;
    private boolean blocked;
    private String role;
    private Date createDate;
    private Date modifyDate;

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public UserBank getBank() {
        return bank;
    }

    public void setBank(UserBank bank) {
        this.bank = bank;
    }

    public UserBill getBill() {
        return bill;
    }

    public void setBill(UserBill bill) {
        this.bill = bill;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
