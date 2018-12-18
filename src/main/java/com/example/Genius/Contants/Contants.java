package com.example.Genius.Contants;

import java.util.HashMap;

/**
 * 全局常量类：
 *  - 所有固有常量都应定义在这里并且加上注释，以免出现魔法值
 *  - 同一类的常量应新建一个静态内部类，并将常量定义在其中
 *  - 代表常量的变量名应该全大写
 * */
public class Contants {
    /**
     * */
    private Contants(){}//私有化构造函数
    public static class cookies{
        public static final String LOGIN_TICKET_NAME = "LoginTicket";// cookies中存有用户登录token的字段名称
    }

    public static class loginTicket{
        public static final int LOGIN_STATUS  = 1;//loginTicket表中status字段为1时代表该用户已登录
        public static final int LOGOUT_STATUS = 0;//loginTicket表中status字段为0时代表该用户已下线
    }
    public static class userNotify{
        public static final int TYPE_ANNOUNCE = 0;
        public static final int TYPE_REMINDER = 1;
        public static final int TYPE_MESSAGE = 2;
        public static final int REAND = 0;
        public static final int UNREAD = 1;
    }
    public static class reminder{
        public static final int ACTION_REPLY = 0;
        public static final int ACTION_COMMENT = 1;
        public static final int ACTION_LIKE = 2;
        public static final int ACTION_FOLLOWED = 3;

        public static final int TARGET_TYPE_ARTICLE = 4;
        public static final int TARGET_TYPE_REPLY = 5;
        public static final int TARGET_TYPE_COMMENT = 6;
        public static final int TARGET_TYPE_FOLLOWED_ARTICLE = 7;
        public static final int TARGET_TYPE_YOUSELF = 8;
    }
    public static class userSubscription{
        public static final int SUBSCRIBE = 1;
        public static final int UNSUBSCRIBE = 0;
        public static final HashMap<Integer,String> ROLES = new HashMap<Integer, String>(){
            {
                put(0,"我发布的帖子被回帖");
                put(1,"我发布的帖子被评论");
                put(2,"我发布的帖子被点赞");
                put(3,"我发布的回帖被回帖");
                put(4,"我发布的回帖被评论");
                put(5,"我发布的回帖被点赞");
                put(6,"我发布的评论被点赞");
                put(7,"我被关注");
                put(8,"我关注的帖子有新回帖");
            }
        };
    }
    public static class announce{
        //public static final int STATUS_
    }
}
