package com.example.Genius.controller;

import com.example.Genius.DAO.UserDAO;
import com.example.Genius.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.example.Genius.Contants.Contants.cookies.LOGIN_TICKET_NAME;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path={"/registerAndLogin"},method={RequestMethod.GET})  //登录或者注册页面
    public String registerAndLogin(){
        return "login.html";
    }

    @RequestMapping(path={"/register/"},method ={RequestMethod.POST}) // 注册端
    @ResponseBody
    public String register(@RequestParam(value="userNickname") String userNickname,
                           @RequestParam(value="userEmail") String userEmail,
                           @RequestParam(value="password") String password,
                           @RequestParam(value = "rememberMe",defaultValue = "false") Boolean rememberMe,
                           HttpServletResponse response){
        // TODO:注册时默认订阅所有的规则
        Map<String,Object> map = userService.register(userNickname,userEmail,password,rememberMe);           //调用userService接口进行注册
        if(map.containsKey(LOGIN_TICKET_NAME))          //表明注册成功
        {
            Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
            cookie.setPath("/");        // 设置为在同一应用服务器下共享
            response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页
            //map.get("msg");
            return "";               //注册成功，用户处于登陆状态，下发ticket, 返回首页。并進行渲染
        }
        else{
          return "";
        }
    }

    @RequestMapping(path="/login/",method={RequestMethod.POST}) // 登录端
    public String login(@RequestParam(value = "userEmail") String userEmail,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value="rememberMe",defaultValue = "false") Boolean rememberMe,
                        HttpServletResponse response) {
        try{
            Map<String,Object> map = userService.login(userEmail,password,rememberMe);
            if(map.containsKey(LOGIN_TICKET_NAME)) {
                Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
                cookie.setPath("/");        // 设置为在同一应用服务器下共享
                response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页
                return "redirect:/";
            }
            else{
              //
                return "";
            }
        }catch (Exception e){
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/logout",method={RequestMethod.POST}) // 登出端
    public String logout(@CookieValue(LOGIN_TICKET_NAME) String ticket){            //此参数从客户端发送过来
        userService.logout(ticket);
        return "redirect:/";
    }

}
