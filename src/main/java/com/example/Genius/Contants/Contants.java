package com.example.Genius.Contants;

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

}
