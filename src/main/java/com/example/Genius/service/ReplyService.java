package com.example.Genius.service;

import com.example.Genius.DAO.CommentDAO;
import com.example.Genius.DAO.ReplyDAO;
import com.example.Genius.model.Comment;
import com.example.Genius.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    ReplyDAO replyDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addReply(Reply reply){            //  为某条评论添加回复
        // html 标签过滤
        reply.setReplyContent(HtmlUtils.htmlEscape(reply.getReplyContent()));

        // 敏感词过滤，前缀树 + 三指针
        reply.setReplyContent(sensitiveService.filter(reply.getReplyContent()));

        return replyDAO.addReply(reply) > 0 ? 1 : 0;
    }

    public List<Reply> selectBycommentID(int commentId) {             // 查找某条评论下的所有回复
        return replyDAO.selectByCommentId(commentId);
    }

    public int getReplyCount(int commentId){
        return replyDAO.getReplyCount(commentId);
    }

  /*  public void deleteComment(int entityId,int entityType){
        commentDAO.updateStatus(entityId,entityType,1);   // 设置为1表示评论删除
    }                  */
}
