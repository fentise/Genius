package com.example.Genius;

import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.*;
import com.example.Genius.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import com.example.Genius.utils.GeneralUtils;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {


    private final static Logger logger = LoggerFactory.getLogger(DatabaseTest.class);
    @Autowired
    private UserNotifyDAO userNotifyDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private ReminderDAO reminderDAO;
    @Autowired
    private AnnounceDAO announceDAO;
    @Autowired
    private UserSubscriptionDAO userSubscriptionDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ArticleDAO articleDAO;
    @Test()
    public void mainTest(){
        //测试数据部分
         // 造一批用户
//        for(int i = 0;i<50;i++){
//            userDAO.add(new User(String.format("userName%d",i),GeneralUtils.MD5("password"+"salt"),"salt",String.format("user%d@qq.com",i),String.format("/userProfilePhoto/%d",i),String.format("/profile/%d",i),0,0));
//        }
        // 插入第一条topic记录，下面将引用他的oId
//        for(int i = 1;i<=20;i++){ // 每个用户发布5条帖子
//            for(int j = 1;j<=5;j++){
//                Article article = new Article();
//                article.setArticleAuthorId(i);
//                article.setArticleContent(String.format("article-%d created by user %d",j,i));
//                article.setArticleLikeCount(0);                     //点赞数
//                article.setArticleReplyCount(0);					//回复数
//                article.setArticleStatus(-1);
//                article.setArticleTitle(String.format("article-%d title created by user %d",j,i));
//                article.setArticleTopicId(1);
//                article.setArticleURL("");
//                article.setArticleViewCount(-1);
//
//                Date date = new Date();
//                date.setTime(date.getTime() + 100 * 24 * 3600);
//                article.setCreateTime(date);
//                date.setTime(date.getTime() + 1000 * 24 * 3600);
//                article.setLatestUpdateTime(date);
//
//                articleDAO.addArticle(article);
//            }
//        }
//-------------------------------------------------------
//        int total = 20;
//        for(int i = 1 ;i<=total;i++){
//            announceDAO.add(new Announce(String.format("announce=%d",i), new Date()));
//            for(int j = 1;j<=5;j++){
//                Message message = new Message(i,String.format("message from %d to %d",i,(i+j)%total+1),new Date(),(i+j)%total+1);
//                messageDAO.add(message);
//                userNotifyDAO.add(new UserNotify(i,new Date(),Contants.userNotify.UNREAD,message.getoId(),Contants.userNotify.TYPE_MESSAGE));
//            }
//            //涵盖所有提醒类型，关注的帖子除外
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_LIKE,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_REPLY,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_COMMENT,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_LIKE,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_REPLY,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_COMMENT,new Date()));
//
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_LIKE,new Date()));
//            reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_YOUSELF,Contants.reminder.ACTION_FOLLOWED,new Date()));
//            //reminderDAO.add(new Reminder(i,i+1, Contants.reminder.TARGET_TYPE_FOLLOWED_ARTICLE,Contants.reminder.ACTION_REPLY,new Date()));
//
//            //订阅全部规则
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_LIKE,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_REPLY,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_COMMENT,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_LIKE,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_REPLY,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_REPLY,Contants.reminder.ACTION_COMMENT,new Date()));
//
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_LIKE,new Date()));
//            userSubscriptionDAO.add(new UserSubscription(i,Contants.reminder.TARGET_TYPE_YOUSELF,Contants.reminder.ACTION_FOLLOWED,new Date()));
//        }

// 测试UserNotifyService部分
    //userNotifyDAO.queryMessageNotifyListOfSession(1,50);


    }
}
