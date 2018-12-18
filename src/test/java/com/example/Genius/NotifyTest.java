package com.example.Genius;

import com.alibaba.fastjson.JSON;
import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.*;
import com.example.Genius.model.Announce;
import com.example.Genius.model.Message;
import com.example.Genius.model.Reminder;
import com.example.Genius.service.NotifyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotifyTest {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseTest.class);
//    @Autowired
//    private UserNotifyDAO userNotifyDAO;
//    @Autowired
//    private MessageDAO messageDAO;
//    @Autowired
//    private ReminderDAO reminderDAO;
//    @Autowired
//    private AnnounceDAO announceDAO;
//    @Autowired
//    private UserSubscriptionDAO userSubscriptionDAO;
//    @Autowired
//    private UserDAO userDAO;
    @Autowired
    private NotifyService notifyService;
    @Test
    public void mainTest(){
//        Random rand =new Random(25);
//        for(int i = 1;i<20;i++){// 每个用户回5个帖子，产生5个提醒
//            for(int j = 1;j<=5;j++){
//                notifyService.createNotify(new Reminder(i,rand.nextInt(20)*5, Contants.reminder.TARGET_TYPE_ARTICLE,Contants.reminder.ACTION_REPLY,new Date()));
//            }
//        }
//
//        for(int i = 0;i<20;i++){
//            notifyService.createNotify(new Announce(String.format("announce- %d",i),new Date()));
//        }

//        for(int i = 1;i<10;i++){
//            for(int j = 1;j<=5;j++){// 前十个用户循环给5个用户发消息
//                int receiverId = (i+j)%10+1;
//                if (receiverId == i){ //避免发给自己
//                    receiverId = 5;
//                }
//                for(int t = 1;t<100;t++){//每个对话100条消息
//                    notifyService.createNotify(new Message(i,String.format("message-%d from %d to %d",t,i,receiverId),new Date(),receiverId,notifyService.getSessionId(i,receiverId)));
//                }
//            }
//        }
//        for(int i = 11;i<=20;i++){
//            for(int j = 1;j<=5;j++){// 后十个用户循环给5个用户发消息
//                int receiverId = (i+j)%10+11;
//                if (receiverId == i){
//                    receiverId = 15;
//                }
//                for(int t = 1;t<2;t++){//每个对话2条消息
//                    notifyService.createNotify(new Message(i,String.format("message-%d from %d to %d",t,i,receiverId),new Date(),receiverId,notifyService.getSessionId(i,receiverId)));
//                }
//            }
//        }

//        for(int i = 1;i<20;i++){
//            // 获取订阅规则
//            HashMap<Integer, HashMap<Integer,Integer>> userSub = notifyService.getUserSubscription(i);
//            logger.error(JSON.toJSONString(userSub));
//            // 拉取公告和提醒
//            logger.error("------------------------------before pull -----------------");
//            HashMap<Integer, Announce> announceHashMap = new HashMap<>();
//            HashMap<Integer, Reminder> reminderHashMap = new HashMap<>();
//            HashMap<Integer, Message> messageHashMap = new HashMap<>();
//            HashMap<Integer,Integer> readStatusMap = new HashMap<>();
//            notifyService.getLatestNotify(i,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
//            HashMap<String,Object> result = new HashMap<>();
//            result.put("announces",announceHashMap);
//            result.put("reminders",reminderHashMap);
//            result.put("messages",messageHashMap);
//            result.put("readStatusList",readStatusMap);
//            logger.error(JSON.toJSONString(result));
//            logger.error("------------------------------------------------------------");
//            notifyService.pullAnnounce(i);
//            notifyService.pullReminder(i);
//            logger.error("-----------------------------after pull----------------------");
//            announceHashMap = new HashMap<>();
//            reminderHashMap = new HashMap<>();
//            messageHashMap = new HashMap<>();
//            readStatusMap = new HashMap<>();
//            notifyService.getLatestNotify(i,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
//            result = new HashMap<>();
//            result.put("announces",announceHashMap);
//            result.put("reminders",reminderHashMap);
//            result.put("messages",messageHashMap);
//            result.put("readStatusList",readStatusMap);
//            logger.error(JSON.toJSONString(result));
//            logger.error("------------------------------------------------------------");
//        }

    }
}
