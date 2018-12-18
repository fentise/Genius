package com.example.Genius.service;

import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.LoginTicketMapper;
import com.example.Genius.DAO.UserMapper;
import com.example.Genius.model.LoginTicket;
import com.example.Genius.model.User;
import com.example.Genius.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    private String getUserHomePageURL(){
        return String.format("/profile/%d",new Random().nextInt(10000));
    }

    /**
    * @param
    * @return map<String,Object>当注册成功是带有键为“loginTicket"的键值对，不然则没有
    * */
    public Map<String,Object> register(String userNickname, String userEmail, String password){
        Map<String,Object> map = new HashMap<String,Object>();
        try{

            if(StringUtils.isEmpty(userEmail)){
                map.put("msg","登录邮箱不能为空");
                return map;
            }
            if(!GeneralUtils.isEmailAddress(userEmail)){
                map.put("msg","邮箱格式非法");
                return map;
            }
            if(userMapper.selectByUserEmail(userEmail) != null){
                map.put("msg","该邮箱已被注册");
                return map;
            }
            if(StringUtils.isEmpty(password)) {
                map.put("msg", "登录密码不能为空");
                return map;
            }
            if(userNickname == null){
                map.put("msg","用户名不能为空");
                return map;
            }
            //logger.error("nickName="+userNickname);
            User user = new User();
            user.setUserNickname(userNickname);
            user.setUserEmail(userEmail);
            user.setUserHomePageURL(getUserHomePageURL());

            //均默认为0
            user.setUserRole(0);
            user.setUserStatus(0);

            String userProfilePhoto = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
            //String userProfilePhoto = String.format("h%d", new Random().nextInt(1000));
            user.setUserProfilePhoto(userProfilePhoto);

            user.setUserSalt(UUID.randomUUID().toString().substring(0, 5));
            user.setUserPassword(GeneralUtils.MD5(password+user.getUserSalt()));
            userMapper.add(user);
            //return this.userMapper.add(user);

         //   User user1 = userMapper.selectByUserEmail(userEmail);

            String loginTicket = addLoginTicket(userMapper.selectByUserEmail(userEmail).getoId(),false);
            map.put(Contants.cookies.LOGIN_TICKET_NAME,loginTicket);
            return map;
            }
        catch(Exception e) {
            logger.error("Fail to insert new user into database : " + e.getMessage());
            map.put("msg", "fail to insert new user into database");
            return map;
        }
    }

    public Map<String,Object> login(String userEmail,String password,boolean rememberMe){
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(userEmail)){
            map.put("msg","登录邮箱不能为空");
            return map;
        }
        if(!GeneralUtils.isEmailAddress(userEmail)){
            map.put("msg","邮箱格式非法");
            return map;
        }
        if(StringUtils.isEmpty(password)) {
            map.put("msg", "登录密码不能为空");
            return map;
        }
        User user = userMapper.selectByUserEmail(userEmail);

        if( user == null){
            map.put("msg","该邮箱未被注册");
            return map;
        }
        if(!user.getUserPassword().equals(GeneralUtils.MD5(password+user.getUserSalt()))){
            map.put("msg","登录密码不正确");
            return map;
        }

        /**
         * 表明用户成功登陆，就像用户下发ticket
         */
        String loginTicket = addLoginTicket(user.getoId(),rememberMe);
        map.put(Contants.cookies.LOGIN_TICKET_NAME,loginTicket);          // 下发ticket
        return map;
    }

    private String addLoginTicket(int userId,boolean rememberMe){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        if(rememberMe){
            date.setTime(date.getTime()+1000*3600*24*7);//默认保持七天有效期 TODO:当用户一直不下线时需要主动重设有效期
            loginTicket.setExpired(date);
        }else{
            date.setTime(date.getTime()+1000*3600*24);//默认保持一天 TODO:其实这里应该设置为离线后保持五分钟的免登录时间
            loginTicket.setExpired(date);
        }
        loginTicket.setStatus(Contants.loginTicket.LOGIN_STATUS);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketMapper.add(loginTicket);
        return loginTicket.getTicket();
    }

    public boolean logout(String loginTicket){
        loginTicketMapper.updateStatus(loginTicket,Contants.loginTicket.LOGOUT_STATUS);
        return true;
    }

    public User getUserById(int id) {
        return userMapper.selectByUserId(id);
    }
}
