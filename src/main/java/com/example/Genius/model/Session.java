package com.example.Genius.model;

public class Session {
    /**
     * @Description:  session作为两个用户之间对话的标识
     */
    private int oId;
    private int userId1;
    private int userId2;

    public Session(int userId1, int userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
    public Session(){

    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }
}
