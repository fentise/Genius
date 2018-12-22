package com.example.Genius.service;

import com.example.Genius.DAO.CommentDAO;
import com.example.Genius.model.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addComment(Comment comment){
        // html 标签过滤
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));

        // 敏感词过滤，前缀树 + 三指针
        comment.setContent(sensitiveService.filter(comment.getContent()));

        return commentDAO.addComment(comment) > 0 ? 1 : 0;
    }

    public List<Comment> selectByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId,entityType);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public void deleteComment(int entityId,int entityType){
        commentDAO.updateStatus(entityId,entityType,1);   // 设置为1表示评论删除
    }

    public void updateCommentReplyCount(int commentId,int count) {
        commentDAO.updateReplyCount(commentId,count);
    }

    public int getCommentStatusById(int userId,int entityId,int entityType) {
        return commentDAO.getCommentStatusById(userId, entityId, entityType) > 0 ? 1 : 0;
    }
}
