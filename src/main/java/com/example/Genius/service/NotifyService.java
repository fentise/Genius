package com.example.Genius.service;

import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.*;
import com.example.Genius.controller.LoginController;
import com.example.Genius.model.*;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NotifyService {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private AnnounceDAO announceDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private ReminderDAO reminderDAO;
    @Autowired
    private UserNotifyDAO userNotifyDAO;
    @Autowired
    private UserSubscriptionDAO userSubscriptionDAO;

    @Autowired
    private SessionDAO sessionDAO;
    public  void createNotify(Announce announce){
        announceDAO.add(announce);
    }
    public  void createNotify(Reminder reminder){
        reminderDAO.add(reminder);
    }

    /**
     * @Description:  消息发送者手动创建message类型消息，将自动推送到接收方的用户消息队列中
     * @param: message
     */
    public  void createNotify(Message message){
        messageDAO.add(message);
        //将message加入接收方的userNotify
        UserNotify userNotify = new UserNotify();
        userNotify.setCreateTime(message.getCreateTime());
        userNotify.setHasRead(Contants.userNotify.UNREAD);
        userNotify.setNotifyId(message.getoId());
        userNotify.setNotifyType(Contants.userNotify.TYPE_MESSAGE);
        userNotify.setUserId(message.getReceiverId());
        userNotifyDAO.add(userNotify);
        //将message插入发送方的userNotify
        userNotify = new UserNotify();
        userNotify.setCreateTime(message.getCreateTime());
        userNotify.setHasRead(Contants.userNotify.REAND);
        userNotify.setNotifyId(message.getoId());
        userNotify.setNotifyType(Contants.userNotify.TYPE_MESSAGE);
        userNotify.setUserId(message.getSenderId());
        userNotifyDAO.add(userNotify);
    }

    public void readUserNotify(int oId){
        userNotifyDAO.setHasRead(oId);
    }
    /**
     * @Description: 获取指定用户的reminder/message类型的消息的前50条，messageList则是所有会话的各前50条
     * @param: userId 
     * @param: announceList
     * @param: reminderList
     * @param: messageList
     * @param: readStatusList
     */
    public  void getLatestNotify(int userId,HashMap<Integer,Announce> announceList,HashMap<Integer,Reminder> reminderList,HashMap<Integer,Message> messageList,HashMap<Integer,Integer> readStatusList){
        List<UserNotify> announceNotify = userNotifyDAO.queryLatestNotifyListWithType(userId,Contants.userNotify.TYPE_ANNOUNCE,50);

        for(UserNotify annoNotify:announceNotify){
            announceList.put(annoNotify.getoId(),announceDAO.queryAnnounce(annoNotify.getNotifyId()));
            readStatusList.put(annoNotify.getoId(),annoNotify.getHasRead());
        }

        List<UserNotify> reminderNotify = userNotifyDAO.queryLatestNotifyListWithType(userId,Contants.userNotify.TYPE_REMINDER,50);
        for(UserNotify reNotify:reminderNotify){
            reminderList.put(reNotify.getoId(),reminderDAO.queryReminder(reNotify.getNotifyId()));
            readStatusList.put(reNotify.getoId(),reNotify.getHasRead());
        }

        // TODO:message按会话方式进行查询-->测试一下
        List<UserNotify> messageNotify = userNotifyDAO.queryMessageNotifyListOfSession(userId,50);
        for(UserNotify meNotify:messageNotify){
            messageList.put(meNotify.getoId(),messageDAO.queryMessage(meNotify.getNotifyId()));
            readStatusList.put(meNotify.getoId(),meNotify.getHasRead());
        }

    }
    public  void getNotifyAfterTime(int userId,Date time,HashMap<Integer,Announce> announceList,HashMap<Integer,Reminder> reminderList,HashMap<Integer,Message> messageList,HashMap<Integer,Integer> readStatusList){
        // 类似上面的方式，增加时间起始点限制，返回时间起始点之后的所有消息,senderId为自己的message过滤掉，这个工作交给前端吧
        List<UserNotify> announceNotify = userNotifyDAO.queryLatestNotifyListWithTypeAfterTime(userId,Contants.userNotify.TYPE_ANNOUNCE,50,time);
        for(UserNotify annoNotify:announceNotify){
            announceList.put(annoNotify.getoId(),announceDAO.queryAnnounce(annoNotify.getNotifyId()));
            readStatusList.put(annoNotify.getoId(),annoNotify.getHasRead());
        }
        List<UserNotify> reminderNotify = userNotifyDAO.queryLatestNotifyListWithTypeAfterTime(userId,Contants.userNotify.TYPE_REMINDER,50,time);
        for(UserNotify reNotify:reminderNotify){
            reminderList.put(reNotify.getoId(),reminderDAO.queryReminder(reNotify.getNotifyId()));
            readStatusList.put(reNotify.getoId(),reNotify.getHasRead());
        }

        // TODO:message按会话方式进行查询-->测试一下
        List<UserNotify> messageNotify = userNotifyDAO.queryMessageNotifyListOfSessionAfterTime(userId,50,time);
        for(UserNotify meNotify:messageNotify){
            messageList.put(meNotify.getoId(),messageDAO.queryMessage(meNotify.getNotifyId()));
            readStatusList.put(meNotify.getoId(),meNotify.getHasRead());
        }
    }
    public int getSessionId(int userId1,int userId2){
        Session session = sessionDAO.querySession(userId1,userId2);
        if (session == null){
            Session newSession = new Session(userId1,userId2);
            sessionDAO.add(newSession);
            return newSession.getoId();
        }
        return session.getoId();
    }
    public void pullReminder(int userId){
        // 拉取用当前用户有关的新提醒
        //从userNotify中找出最新一条Reminder 的时间戳，查询该时间之后的Reminder
        UserNotify latestReminderNotify = userNotifyDAO.queryLatestNotifyWithType(userId,Contants.userNotify.TYPE_REMINDER);
        logger.error(String.format("latest==null %b",latestReminderNotify == null));
        List<Reminder> reminders = new ArrayList<>();
        if(latestReminderNotify == null){//当前用户没有接收过提醒类消息
            //reminders = reminderDAO.queryReminderList(100);
            // 用户发布的帖子有关
            reminders.addAll(reminderDAO.queryRemindersRelateToArticle(userId,50));
            // 用户发布的回帖有关
            reminders.addAll(reminderDAO.queryRemindersRelateToReply(userId,50));
            // 用户发布的评论有关
            // 用户自身有关
            // 用户关注的帖子有关
        }
        else {
            reminders.addAll(reminderDAO.queryRemindersRelateToArticleAfterTime(userId,latestReminderNotify.getCreateTime(),50));
            reminders.addAll(reminderDAO.queryRemindersRelateToReplyAfterTime(userId,latestReminderNotify.getCreateTime(),50));
            //reminders = reminderDAO.queryReminderAfterTime(latestReminderNotify.getCreateTime(),100);
        }
        //按当前用户的订阅规则进行过滤，将过滤后的结果写入该用户的userNotify
        HashMap<Integer,HashMap<Integer,Integer>> userSubscriptionMap = getUserSubscription(userId);
        for(Reminder reminder:reminders){
            if(userSubscriptionMap.get(reminder.getTargetType()).get(reminder.getAction()) == Contants.userSubscription.UNSUBSCRIBE){
                reminders.remove(reminder);//每一个reminder内部都有唯一的oId，所以使用remove方法是安全的
            }
        }
        for(Reminder reminder:reminders){
            userNotifyDAO.add(new UserNotify(userId,reminder.getCreateTime(),Contants.userNotify.UNREAD,reminder.getoId(),reminder.getTargetType()));
        }
    }

    public void pullAnnounce(int userId){
        //从userNotify中找出最新一条Announce的时间戳，查询出该时间之后的Announce
        UserNotify latestAnnounceNotify = userNotifyDAO.queryLatestNotifyWithType(userId,Contants.userNotify.TYPE_ANNOUNCE);
        List<Announce> announces;
        if(latestAnnounceNotify == null){ //当前用户的user_notify中没有announce类型的消息
            announces = announceDAO.queryAnnounceList(100);
        }
        else{
            announces = announceDAO.queryAnnounceListAfterTime(latestAnnounceNotify.getCreateTime(),100);
        }
        //对于公告暂无过滤规则，直接插入userNotify中
        for(Announce announce:announces){
            userNotifyDAO.add(new UserNotify(userId,announce.getCreateTime(),Contants.userNotify.UNREAD,announce.getoId(),announce.getTargetType()));
        }
    }
    public HashMap<Integer,HashMap<Integer,Integer>> getUserSubscription(int userId){
        /**
         * @Description:
         * @param: userId
         * @return: java.util.HashMap<java.lang.Integer,java.util.HashMap<java.lang.Integer,java.lang.Integer>>
         *          targetType-action-subStatus
         */
        //HashMap没有提供深复制的API，可自行实现，但要求键值对的对象必须实现serialization接口
        List<UserSubscription> userSubscriptions = userSubscriptionDAO.queryUserSubscriptionList(userId);
        HashMap<Integer,HashMap<Integer,Integer>> userSubscriptionMap = new HashMap<>();

        //我发布的主帖-回帖/评论/点赞
        HashMap<Integer,Integer> action = new HashMap<>();
        action.put(Contants.reminder.ACTION_COMMENT,Contants.userSubscription.UNSUBSCRIBE);
        action.put(Contants.reminder.ACTION_REPLY,Contants.userSubscription.UNSUBSCRIBE);
        action.put(Contants.reminder.ACTION_LIKE,Contants.userSubscription.UNSUBSCRIBE);
        userSubscriptionMap.put(Contants.reminder.TARGET_TYPE_ARTICLE,action);

        //我发布的回帖-回帖/评论/点赞
        action = new HashMap<>();
        action.put(Contants.reminder.ACTION_COMMENT,Contants.userSubscription.UNSUBSCRIBE);
        action.put(Contants.reminder.ACTION_REPLY,Contants.userSubscription.UNSUBSCRIBE);
        action.put(Contants.reminder.ACTION_LIKE,Contants.userSubscription.UNSUBSCRIBE);
        userSubscriptionMap.put(Contants.reminder.TARGET_TYPE_REPLY,action);

        // 我发布的评论-点赞
        action = new HashMap<>();
        action.put(Contants.reminder.ACTION_LIKE,Contants.userSubscription.UNSUBSCRIBE);
        userSubscriptionMap.put(Contants.reminder.TARGET_TYPE_COMMENT,action);

        // 我自己-被关注
        action = new HashMap<>();
        action.put(Contants.reminder.ACTION_FOLLOWED,Contants.userSubscription.UNSUBSCRIBE);
        userSubscriptionMap.put(Contants.reminder.TARGET_TYPE_YOUSELF,action);

        // 我关注的帖子有新回帖
        action = new HashMap<>();
        action.put(Contants.reminder.ACTION_REPLY,Contants.userSubscription.UNSUBSCRIBE);
        userSubscriptionMap.put(Contants.reminder.TARGET_TYPE_FOLLOWED_ARTICLE,action);

        // 遍历每一条订阅规则，更新其在userSubscriptionMap中的值
        for(UserSubscription uSubscription:userSubscriptions){
            userSubscriptionMap.get(uSubscription.getTargetType()).put(uSubscription.getAction(),Contants.userSubscription.SUBSCRIBE);
        }
        return userSubscriptionMap;
    }

    public int[] subscriptionReflex(HashMap<Integer,HashMap<Integer,Integer>> subMap){
        int[] result = new int[9];
        result[0] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_REPLY);
        result[1] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_COMMENT);
        result[2] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_LIKE);
        result[3] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_REPLY);
        result[4] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_COMMENT);
        result[5] = subMap.get(Contants.reminder.TARGET_TYPE_ARTICLE).get(Contants.reminder.ACTION_LIKE);
        result[6] = subMap.get(Contants.reminder.TARGET_TYPE_COMMENT).get(Contants.reminder.ACTION_LIKE);
        result[7] = subMap.get(Contants.reminder.TARGET_TYPE_YOUSELF).get(Contants.reminder.ACTION_FOLLOWED);
        result[8] = subMap.get(Contants.reminder.TARGET_TYPE_FOLLOWED_ARTICLE).get(Contants.reminder.ACTION_REPLY);
        return result;
    }

    public int[] subscriptionReflexInverse(int id){
        int[] result = new int[2];
        switch (id){
            case 0:{
                result[0] = Contants.reminder.TARGET_TYPE_ARTICLE;
                result[1] = Contants.reminder.ACTION_REPLY;
            }break;
            case 1:{
                result[0] = Contants.reminder.TARGET_TYPE_ARTICLE;
                result[1] = Contants.reminder.ACTION_COMMENT;
            }break;
            case 2:{
                result[0] = Contants.reminder.TARGET_TYPE_ARTICLE;
                result[1] = Contants.reminder.ACTION_LIKE;
            }break;
            case 3:{
                result[0] = Contants.reminder.TARGET_TYPE_REPLY;
                result[1] = Contants.reminder.ACTION_REPLY;
            }break;
            case 4:{
                result[0] = Contants.reminder.TARGET_TYPE_REPLY;
                result[1] = Contants.reminder.ACTION_COMMENT;
            }break;
            case 5:{
                result[0] = Contants.reminder.TARGET_TYPE_REPLY;
                result[1] = Contants.reminder.ACTION_LIKE;
            }break;
            case 6:{
                result[0] = Contants.reminder.TARGET_TYPE_COMMENT;
                result[1] = Contants.reminder.ACTION_LIKE;
            }break;
            case 7:{result[0] = Contants.reminder.TARGET_TYPE_YOUSELF;
                result[1] = Contants.reminder.ACTION_FOLLOWED;}break;
            case 8:{
                result[0] = Contants.reminder.TARGET_TYPE_FOLLOWED_ARTICLE;
                result[1] = Contants.reminder.ACTION_REPLY;
            }break;
            default:{
                logger.error("mismatch");
                result[0] = -1;
                result[1] = -1;
            }
        }
        return result;
    }
    public boolean updateSubscriptionStatus(int userId,int targetType,int action,int subscriptionStatus){
        /**
         * @Description: subscriptionStatus为订阅则向数据库插入一条该记录，为不订阅则从数据库中删除所有该条件的记录
         * @param: userId
         * @param: targetType
         * @param: action
         * @param: subscriptionStatus
         */
        if (subscriptionStatus == Contants.userSubscription.SUBSCRIBE){
            userSubscriptionDAO.add(new UserSubscription(userId,targetType,action));
        }
        else{
            userSubscriptionDAO.delete(new UserSubscription(userId,targetType,action));
        }
        return true;
    }


}
