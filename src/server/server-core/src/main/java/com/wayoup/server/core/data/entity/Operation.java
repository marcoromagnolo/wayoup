package com.wayoup.server.core.data.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class Operation {

    private Long id;
    private Long userId;
    private Long positionId;
    private String title;
    private String description;
    private Date expectedDate;
    private Integer expectedDuration;
    private Date startDate;
    private Date endDate;
    private String stateType;
    private int nJobsStarted;
    private int nJobsDropped;
    private int nJobsEnded;
    private int nJobsCompleted;
    private Date createDate;
    private Date modifyDate;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "position_id")
    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "expected_date")
    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Basic
    @Column(name = "expected_duration")
    public Integer getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(Integer expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "state_type")
    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    @Basic
    @Column(name = "n_jobs_started")
    public int getnJobsStarted() {
        return nJobsStarted;
    }

    public void setnJobsStarted(int nJobsStarted) {
        this.nJobsStarted = nJobsStarted;
    }

    @Basic
    @Column(name = "n_jobs_dropped")
    public int getnJobsDropped() {
        return nJobsDropped;
    }

    public void setnJobsDropped(int nJobsDropped) {
        this.nJobsDropped = nJobsDropped;
    }

    @Basic
    @Column(name = "n_jobs_ended")
    public int getnJobsEnded() {
        return nJobsEnded;
    }

    public void setnJobsEnded(int nJobsEnded) {
        this.nJobsEnded = nJobsEnded;
    }

    @Basic
    @Column(name = "n_jobs_completed")
    public int getnJobsCompleted() {
        return nJobsCompleted;
    }

    public void setnJobsCompleted(int nJobsCompleted) {
        this.nJobsCompleted = nJobsCompleted;
    }

    @Basic
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

}
