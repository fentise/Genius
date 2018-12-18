package com.example.Genius.model;

import java.util.Date;

public class UserNotify {
    /**
     * @Description: 用户消息列表，推和拉都将与用户有关的消息的id和类型放入这个列表
     */
    private int oId;
    private int userId;
    private Date createTime;
    private int hasRead;
    private int notifyId;
    private int notifyType;

    public UserNotify(int userId,Date createTime,int hasRead,int notifyId,int notifyType){
        this.userId = userId;
        this.createTime = createTime;
        this.hasRead = hasRead;
        this.notifyId = notifyId;
        this.notifyType = notifyType;
    }
    public UserNotify(){
        //无参构造函数
    }
    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }
}
