package com.example.Genius.model;

import java.util.Date;

public class message {
    private int oId;
    private int senderId;
    private String messageContent;
    private Date createTime;
    private int status;
    private int recepterId;

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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
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

    public int getRecepterId() {
        return recepterId;
    }

    public void setRecepterId(int recepterId) {
        this.recepterId = recepterId;
    }
}
