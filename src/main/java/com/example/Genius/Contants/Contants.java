package com.example.Genius.Contants;

import java.text.SimpleDateFormat;

/**
 * 全局常量类：
 *  - 所有固有常量都应定义在这里并且加上注释，以免出现魔法值
 *  - 同一类的常量应新建一个静态内部类，并将常量定义在其中
 *  - 代表常量的变量名应该全大写
 * */


public class Contants {

    public static class THEME {
        public static final int DEFAULT_THEME = 0;              // 默认主题，即全部
        public static final int TECHNOLOGY_THEME = 1;           // 技术交流
        public static final int PROFESSION_THEME = 2;           // 职业发展
        public static final int STUDY_THEME = 3;                // 学习生活
        public static final int RESOURCES_THEME = 4;            // 资源分享
        public static final int QUESTION_THEME = 5;             // 疑难困惑
    }

    public static class ORDER {
        public static final int DEFAULT_ORDER = 0;             // 按最近修改时间排序
        public static final int LIKECOUNT_ORDER = 1;           // 按点赞数目排序
        public static final int COMMENTCOUNT_ORDER = 2;        // 按评论数目排序
    }

    public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    private Contants(){}//私有化构造函数
    public static class cookies{
        public static final String LOGIN_TICKET_NAME = "LoginTicket";// cookies中存有用户登录token的字段名称
    }

    public static class loginTicket{
        public static final int LOGIN_STATUS  = 1;//loginTicket表中status字段为1时代表该用户已登录
        public static final int LOGOUT_STATUS = 0;//loginTicket表中status字段为0时代表该用户已下线
    }

}
