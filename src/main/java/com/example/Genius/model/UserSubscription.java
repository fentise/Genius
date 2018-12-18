package com.example.Genius.model;

import java.util.Date;

public class UserSubscription {
    /**
     * @Description: 订阅规则 数据库中存在即为订阅，不存在即为不订阅
     */
    private int oId;
    private int userId;
    private int targetType;
    private int action;

    public UserSubscription(int userId, int targetType, int action) {
        this.userId = userId;
        this.targetType = targetType;
        this.action = action;
    }
    public UserSubscription(){

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

}
