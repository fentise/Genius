package com.example.Genius.model;

import java.util.Date;

public class Comment {
    private int oId;
    private int commentToId;
    private int commentType;
    private String commentContent;
    private int commentAuthorId;
    private int status;
    private Date createTime;
    private int commentFloor;
    private int likeCount;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getCommentToId() {
        return commentToId;
    }

    public void setCommentToId(int commentToId) {
        this.commentToId = commentToId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentAuthorId() {
        return commentAuthorId;
    }

    public void setCommentAuthorId(int commentAuthorId) {
        this.commentAuthorId = commentAuthorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentFloor() {
        return commentFloor;
    }

    public void setCommentFloor(int commentFloor) {
        this.commentFloor = commentFloor;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
