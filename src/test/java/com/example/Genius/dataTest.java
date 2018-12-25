package com.example.Genius;

import com.alibaba.fastjson.JSON;
import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.ReminderDAO;
import com.example.Genius.model.*;
import com.example.Genius.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class dataTest {
    private final static Logger logger = LoggerFactory.getLogger(dataTest.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReminderDAO reminderDAO;
    @Test
    public void mainTest(){
         //产生基本数据
        //用户填充
//        for(int i = 1;i<=20;i++){
//           userService.register(String.format("userName-%d",i),String.format("%d@qq.com",i),"password");
//        }
//
//        for(int i = 1;i<=20;i++){
//            for(int j = 1;j<=5;j++){
//                // 发帖--每个用户发5个帖子
//                Article article = new Article();
//                article.setArticleContent(String.format("content user-%d articel-%d",i,j));
//                article.setArticleTitle(String.format("title user-%d articel-%d",i,j));
//                article.setArticleAuthorId(i);
//                article.setArticleTopicId(Contants.THEME.PROFESSION_THEME);
//                article.setCreateTime(new Date());
//                article.setLatestUpdateTime(new Date());
//                articleService.addArticle(article);
//                // 其后面的5个用户点赞它
//                for(int t = 1;t<=5;t++){
//                    UserLike userLike = new UserLike();
//                    userLike.setCreateTime(new Date());
//                    userLike.setEntityId(article.getoId());
//                    int userId = (i+t)%20;
//                    if (userId == 0)
//                        userId = 1;
//                    userLike.setUserId(userId);
//                    userLike.setEntityType(EntityType.ENTITY_ARTICLE);
//                    likeService.like(userLike);
//                    Reminder reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
//                    notifyService.createNotify(reminder);
//                }
//                // 其后面的5个用户评论它
//                for(int k = 1;k<=5;k++){
//                    int userId = (i+k)%20;
//                    if(userId == 0)
//                        userId = 1;
//                    Comment comment = new Comment();
//                    comment.setContent(String.format("comment from user-%d to articel-%d",userId,article.getoId()));
//                    comment.setCreateTime(new Date());
//                    comment.setEntityId(article.getoId());
//                    comment.setEntityType(EntityType.ENTITY_ARTICLE);
//
//                    comment.setUserId(userId);
//                    commentService.addComment(comment);
//
//                    Reminder reminder = new Reminder(comment.getUserId(),comment.getEntityId(), Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_COMMENT,comment.getCreateTime());
//                    notifyService.createNotify(reminder);
//
//                    for(int n = 1;n<=5;n++){ // 其后5个用户给他点赞
//                        UserLike userLike = new UserLike();
//                        userLike.setCreateTime(new Date());
//                        userLike.setEntityId(comment.getoId());
//                        userId = (i+k+n)%20;
//                        if (userId == 0)
//                            userId = 1;
//                        userLike.setUserId(userId);
//                        userLike.setEntityType(EntityType.ENTITY_COMMENT);
//                        likeService.like(userLike);
//
//                        reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
//                        notifyService.createNotify(reminder);
//                    }
//                    for(int n = 1;n<=5;n++){ // 其后5个用户回复他
//                        userId = (i+k+n)%20;
//                        if(userId == 0)
//                            userId = 1;
//                        Reply reply = new Reply();
//                        reply.setReplyContent(String.format("reply from user-%d to comment-%d",userId,comment.getoId()));
//                        reply.setCommentId(comment.getoId());
//                        reply.setCreateTime(new Date());
//
//                        reply.setUserId(userId);
//                        reply.setTargetId(comment.getUserId());
//                        replyService.addReply(reply);
//
//
//                        reminder = new Reminder(reply.getUserId(),reply.getCommentId(),Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_REPLY,reply.getCreateTime());
//                        notifyService.createNotify(reminder);
//
//                        for(int q= 1;q<=5;q++){ //其后的5个用户点赞
//                            UserLike userLike = new UserLike();
//                            userLike.setCreateTime(new Date());
//                            userLike.setEntityId(reply.getoId());
//                            userId = (i+k+n+q)%20;
//                            if (userId == 0)
//                                userId = 1;
//                            userLike.setUserId(userId);
//                            userLike.setEntityType(EntityType.ENTITY_REPLY);
//                            likeService.like(userLike);
//
//                            reminder = new Reminder(userLike.getUserId(),userLike.getEntityId(), Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_LIKE,userLike.getCreateTime());
//                            notifyService.createNotify(reminder);
//                        }
//                    }
//                }
//            }
//        }

         //订阅规则
        for(int i = 1;i<=20;i++){
            for(int j = 0;j<Contants.userSubscription.ROLES.size();j++){
                int[] temp = notifyService.subscriptionReflexInverse(j);
                int targetType = temp[0];
                int action = temp[1];
                notifyService.updateSubscriptionStatus(i,targetType,action,Contants.userSubscription.SUBSCRIBE);
            }
        }
 //       reminderDAO.add(new Reminder(10,1400,5,2,new Date()));
        // 消息队列
//        HashMap<Integer,String> announceHashMap = new HashMap<>(); // user_notify.oId:Announce   user_notify.oId 用于设置readStatus
//        HashMap<Integer,String> reminderHashMap = new HashMap<>(); // user_notify.oId:Reminder
//        HashMap<Integer,Message> messageHashMap = new HashMap<>();  // user_notify.oId:Message
//        HashMap<Integer,Integer> readStatusMap = new HashMap<>();  // user_notify.oId:readStatus
//        notifyService.getLatestNotify(2,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
//        HashMap<String,Object> announceResult = new HashMap<>();
//        HashMap<String,Object> reminderResult = new HashMap<>();
//        HashMap<String,Object> readStatusResult = new HashMap<>();
//        announceResult.put("announces",announceHashMap);
//        logger.error(JSON.toJSONString(announceResult));
//        reminderHashMap.forEach((k,v)->{logger.error(k + " = " + v);});
//        reminderResult.put("reminders",reminderHashMap);
//        logger.error(JSON.toJSONString(reminderResult));
//        readStatusResult.put("readStatusList",readStatusMap);
//        logger.error(JSON.toJSONString(readStatusResult));
//        logger.error("----------------------------------------------");
//        announceResult.clear();
//        reminderResult.clear();
//        readStatusResult.clear();
//        announceHashMap.clear();
//        reminderHashMap.clear();
//        messageHashMap.clear();
//        readStatusMap.clear();
//        notifyService.pullReminder(2);
//        notifyService.getLatestNotify(2,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
//        reminderHashMap.forEach((k,v)->{logger.error(k + " = " + v);});
//        announceResult.put("announces",announceHashMap);
//        logger.error(JSON.toJSONString(announceResult));
//        reminderResult.put("reminders",reminderHashMap);
//        logger.error(JSON.toJSONString(reminderResult));
//        readStatusResult.put("readStatusList",readStatusMap);
//        logger.error(JSON.toJSONString(readStatusResult));
    }
}
