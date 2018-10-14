package com.example.Genius.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> currentUsers = new ThreadLocal<User>();
    public User getCurrentUser(){
        return currentUsers.get();
    }

    public void setCurrentUsers(User user) {
        currentUsers.set(user);
    }
    public void clear(){
        currentUsers.remove();
    }
}
