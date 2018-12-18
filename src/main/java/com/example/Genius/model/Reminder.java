package com.example.Genius.model;

import java.util.Date;

public class Reminder {
    private int oId;
    private int senderId;
    private int targetId;
    private int targetType;
    private int action;
    private Date createTime;
    private int status;

    public Reminder(int senderId, int targetId, int targetType, int action, Date createTime) {
        this.senderId = senderId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.action = action;
        this.createTime = createTime;
    }
    public Reminder(){

    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
