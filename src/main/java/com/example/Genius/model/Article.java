package com.example.Genius.model;

import java.util.Date;

public class Article {
    private int oId;
    private String articleTitle;
    private int articleAuthorId;
    private String articleContent;
    private int articleReplyCount;           //  文章评论数
    private int articleLikeCount;
    private int articleViewCount;
    private int articleTopicId;              // 对应的主题分区
    private int articleStatus;
    private Date createTime;
    private Date latestUpdateTime;

    public int getoId() { return oId; }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public int getArticleAuthorId() {
        return articleAuthorId;
    }

    public void setArticleAuthorId(int articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }


    public int getArticleReplyCount() {
        return articleReplyCount;
    }

    public void setArticleReplyCount(int articleReplyCount) {
        this.articleReplyCount = articleReplyCount;
    }

    public int getArticleLikeCount() {
        return articleLikeCount;
    }

    public void setArticleLikeCount(int articleLikeCount) {
        this.articleLikeCount = articleLikeCount;
    }

    public int getArticleViewCount() {
        return articleViewCount;
    }

    public void setArticleViewCount(int articleViewCount) {
        this.articleViewCount = articleViewCount;
    }

    public int getArticleTopicId() {
        return articleTopicId;
    }

    public void setArticleTopicId(int articleTopicId) {
        this.articleTopicId = articleTopicId;
    }

    public int getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(int articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }
}
