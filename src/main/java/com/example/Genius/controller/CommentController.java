package com.example.Genius.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.*;
import com.example.Genius.service.ArticleService;
import com.example.Genius.service.CommentService;
import com.example.Genius.service.NotifyService;
import com.example.Genius.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @Autowired
    private NotifyService notifyService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addComment(@RequestParam("articleId") int articleId,
                             @RequestParam("content") String content){
        try{
            Comment comment = new Comment();

            if(hostHolder.getCurrentUser() != null) {  // 判断当前是否有登录的用户，设置评论的发布者
                comment.setUserId(hostHolder.getCurrentUser().getoId());
            }else {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("code",1);
                jsonObject.put("msg","用户未登录");          // 同样，此处可以做登录跳转

                return jsonObject.toJSONString();
            }

            comment.setCommentReplyCount(0);
            comment.setCreateTime(new Date());
            comment.setLikeCount(0);
            comment.setStatus(0);
            comment.setContent(content);
            comment.setEntityType(EntityType.ENTITY_ARTICLE);
            comment.setEntityId(articleId);

            commentService.addComment(comment);
            int count = commentService.getCommentCount(articleId,EntityType.ENTITY_ARTICLE); // 添加评论后，同步更新评论数目
            articleService.updateArticleCommentCount(comment.getEntityId(),count);

            // 用户评论时，生成对帖子发布者的提醒
            Reminder reminder = new Reminder(hostHolder.getCurrentUser().getoId(),comment.getEntityId(), Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_COMMENT,comment.getCreateTime());
            notifyService.createNotify(reminder);

        }catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return GeneralUtils.getJSONString(0);
    }
}
