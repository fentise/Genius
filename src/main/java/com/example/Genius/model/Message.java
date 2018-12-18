package com.example.Genius.model;

import java.util.Date;

public class Message {
    private int oId;
    private int senderId;
    private String messageContent;
    private Date createTime;
    private int status;
    private int receiverId;
    private int sessionId;
    public Message(int senderId, String messageContent, Date createTime, int receiverId,int sessionId) {
        this.senderId = senderId;
        this.messageContent = messageContent;
        this.createTime = createTime;
        this.receiverId = receiverId;
        this.sessionId = sessionId;
    }

    public Message(){

    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
