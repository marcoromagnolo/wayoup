package com.wayoup.server.core.data.entity;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class UserBill extends AbstractEntity {

    private Integer totalBalance;
    private Integer donationBalance;
    private Integer jobBalance;
    private Integer currency;
    private Integer operations;
    private Integer donations;
    private Integer jobs;
    private Date createDate;
    private Date modifyDate;

    public Integer getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Integer totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Integer getDonationBalance() {
        return donationBalance;
    }

    public void setDonationBalance(Integer donationBalance) {
        this.donationBalance = donationBalance;
    }

    public Integer getJobBalance() {
        return jobBalance;
    }

    public void setJobBalance(Integer jobBalance) {
        this.jobBalance = jobBalance;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getOperations() {
        return operations;
    }

    public void setOperations(Integer operations) {
        this.operations = operations;
    }

    public Integer getDonations() {
        return donations;
    }

    public void setDonations(Integer donations) {
        this.donations = donations;
    }

    public Integer getJobs() {
        return jobs;
    }

    public void setJobs(Integer jobs) {
        this.jobs = jobs;
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
