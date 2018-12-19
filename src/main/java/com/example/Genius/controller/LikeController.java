package com.example.Genius.controller;

import com.example.Genius.Contants.Contants;
import com.example.Genius.model.EntityType;
import com.example.Genius.model.HostHolder;
import com.example.Genius.model.Reminder;
import com.example.Genius.model.UserLike;
import com.example.Genius.service.LikeService;
import com.example.Genius.service.NotifyService;
import com.example.Genius.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class LikeController {

    @Autowired
    HostHolder hostHolder;        // 登陆拦截器中存储的用户，ThreadLocal<User>

    @Autowired
    LikeService likeService;

    @Autowired
    private NotifyService notifyService;
    @RequestMapping(path={"/likeComment"},method={RequestMethod.POST})
    @ResponseBody
    public String likeComment(@RequestParam("commentId") int commentId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        UserLike userLike = new UserLike();
        userLike.setUserId(hostHolder.getCurrentUser().getoId());
        userLike.setCreateTime(new Date());
        userLike.setEntityId(commentId);
        userLike.setEntityType(EntityType.ENTITY_COMMENT);
        userLike.setStatus(0);                                // 0 表示喜欢

        int likeCount = likeService.like(userLike);

        // 新增评论点赞时，产生对评论发布者的一条提醒
        Reminder reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
        notifyService.createNotify(reminder);

        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/dislikeCommment"},method={RequestMethod.POST})
    @ResponseBody
    public String dislikeComment(@RequestParam("commentId") int commentId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        long likeCount = likeService.dislike(hostHolder.getCurrentUser().getoId(), EntityType.ENTITY_COMMENT,commentId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/likeArticle"},method={RequestMethod.POST})
    @ResponseBody
    public String likeArticle(@RequestParam("articleId") int articleId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        UserLike userLike = new UserLike();
        userLike.setUserId(hostHolder.getCurrentUser().getoId());
        userLike.setCreateTime(new Date());
        userLike.setEntityId(articleId);
        userLike.setEntityType(EntityType.ENTITY_ARTICLE);
        userLike.setStatus(0);                                // 0 表示喜欢

        int likeCount = likeService.like(userLike);

        // 新增帖子点赞时，产生对评论发布者的一条提醒
        Reminder reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
        notifyService.createNotify(reminder);

        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/dislikeArticle"},method={RequestMethod.POST})
    @ResponseBody
    public String dislikeArticle(@RequestParam("articleId") int articleId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        long likeCount = likeService.dislike(hostHolder.getCurrentUser().getoId(), EntityType.ENTITY_ARTICLE,articleId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/likeReply"},method={RequestMethod.POST})
    @ResponseBody
    public String likeReply(@RequestParam("replyId") int replyId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        UserLike userLike = new UserLike();
        userLike.setUserId(hostHolder.getCurrentUser().getoId());
        userLike.setCreateTime(new Date());
        userLike.setEntityId(replyId);
        userLike.setEntityType(EntityType.ENTITY_REPLY);
        userLike.setStatus(0);                                // 0 表示喜欢

        int likeCount = likeService.like(userLike);

        // 新增回复点赞时，产生对评论发布者的一条提醒
        Reminder reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
        notifyService.createNotify(reminder);

        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/dislikeReply"},method={RequestMethod.POST})
    @ResponseBody
    public String dislikeReply(@RequestParam("replyId") int replyId){
        if(hostHolder.getCurrentUser() == null) {
            return GeneralUtils.getJSONString(1,"用户未登录");
        }

        long likeCount = likeService.dislike(hostHolder.getCurrentUser().getoId(), EntityType.ENTITY_REPLY,replyId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }
}
