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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
public class LikeController {

//    @Autowired
//    HostHolder hostHolder;        // 登陆拦截器中存储的用户，ThreadLocal<User>

    @Autowired
    LikeService likeService;

    @Autowired
    private NotifyService notifyService;
    @RequestMapping(path={"/likeComment"},method={RequestMethod.POST})
    @ResponseBody
    public String likeComment(@RequestBody Map<String,Object> map){

        String userId = map.get("userId").toString();
        int commentId = (Integer)map.get("commentId");

//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }

        UserLike userLike = new UserLike();
        userLike.setUserId(Integer.parseInt(userId));
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
    public String dislikeComment(@RequestBody Map<String,Object> map){

        String userId = map.get("userId").toString();
        int commentId = (Integer)map.get("commentId");

//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }

        long likeCount = likeService.dislike(Integer.parseInt(userId), EntityType.ENTITY_COMMENT,commentId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/likeArticle"},method={RequestMethod.POST})
    @ResponseBody
    public String likeArticle(@RequestBody Map<String,Object> map){

        String userId = map.get("userId").toString();
        int articleId = (Integer)map.get("articleId");

//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }
        UserLike userLike = new UserLike();
        userLike.setUserId(Integer.parseInt(userId));
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
    public String dislikeArticle(@RequestBody Map<String,Object> map){

        String userId = map.get("userId").toString();
        int articleId = (Integer)map.get("articleId");

//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }

        long likeCount = likeService.dislike(Integer.parseInt(userId), EntityType.ENTITY_ARTICLE,articleId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/likeReply"},method={RequestMethod.POST})
    @ResponseBody
    public String likeReply(@RequestBody Map<String,Object> map){
//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }
        String userId = map.get("userId").toString();
        int replyId = (Integer)map.get("replyId");

        UserLike userLike = new UserLike();
        userLike.setUserId(Integer.parseInt(userId));
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
    public String dislikeReply(@RequestBody Map<String,Object> map){

        String userId = map.get("userId").toString();
        int replyId = (Integer)map.get("replyId");
//        if(hostHolder.getCurrentUser() == null) {
//            return GeneralUtils.getJSONString(1,"用户未登录");
//        }

        long likeCount = likeService.dislike(Integer.parseInt(userId), EntityType.ENTITY_REPLY,replyId);
        return GeneralUtils.getJSONString(0,String.valueOf(likeCount));
    }
}
