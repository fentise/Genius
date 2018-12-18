package com.example.Genius.model;

public class User {
    private int oId; // auto increment
    private String userNickname;
    private String userPassword;
    private String userSalt;
    private String userEmail;
    private int userRole;
    private int userStatus;
    private String userProfilePhoto;

    public String getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
    }

    public User(){

    }
    public User(String userNickname,String userPassword,String userSalt,String userEmail,String userProfilePhoto,int userRole,int userStatus){
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
        this.userEmail = userEmail;
        this.userProfilePhoto = userProfilePhoto;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}
