package com.wayoup.server.core.data.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class Notification {

    private Long id;
    private String actionType;
    private Integer userId;
    private Integer operationId;
    private Integer jobId;
    private Integer jobRequestId;
    private Integer jobResponseId;
    private Integer feedbackId;
    private Date createDate;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action_type")
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "operation_id")
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "job_id")
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "job_request_id")
    public Integer getJobRequestId() {
        return jobRequestId;
    }

    public void setJobRequestId(Integer jobRequestId) {
        this.jobRequestId = jobRequestId;
    }

    @Basic
    @Column(name = "job_response_id")
    public Integer getJobResponseId() {
        return jobResponseId;
    }

    public void setJobResponseId(Integer jobResponseId) {
        this.jobResponseId = jobResponseId;
    }

    @Basic
    @Column(name = "feedback_id")
    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
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

}
