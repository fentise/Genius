package com.example.Genius.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    /**
     *  用来在登录时区分不同的用户，所有的对象公用一个currentUsers，然后不同的线程读取出不同的user
     *      是一个线程本地变量
     */
    private static ThreadLocal<User> currentUsers = new ThreadLocal<User>();
    public User getCurrentUser(){ return currentUsers.get(); }
    public void setCurrentUsers(User user) {
        currentUsers.set(user);
    }
    public void clear(){
        currentUsers.remove();
    }
}
