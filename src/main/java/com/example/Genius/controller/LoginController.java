package com.example.Genius.controller;

import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.UserMapper;
import com.example.Genius.model.User;
import com.example.Genius.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(path={"/registerAndLogin"})
    public String registerAndLogin(){
        return "login";
    }

    @RequestMapping(path={"/register"},method ={RequestMethod.POST})
    public String register(@RequestParam(value="userNickname",defaultValue="1") String userNickname,
                           @RequestParam(value="userEmail") String userEmail,
                           @RequestParam(value="password") String password,
                           HttpServletResponse response,
                           Model model){
        Map<String,Object> map = userService.register(userNickname,userEmail,password);           //调用userService接口进行注册
        if(map.isEmpty()) {
            //User user = userMapper.selectByUserEmail(userEmail);
            //model.addAttribute("user",user);
            return "redirect:/index";
        }
        else
            {
                model.addAttribute("msg",map.get("msg"));
                return "error";
            }
    }

    @RequestMapping(path="/login",method={RequestMethod.POST})
    public String login(@RequestParam(value = "userEmail") String userEmail,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value="rememberMe",defaultValue = "false") Boolean rememberMe,
                        Model model,
                        HttpServletResponse response) {
        Map<String,Object> map = userService.login(userEmail,password,rememberMe);
        User user = userMapper.selectByUserEmail(userEmail);
        if(map.containsKey(Contants.cookies.LOGIN_TICKET_NAME)) {
            Cookie cookie = new Cookie(Contants.cookies.LOGIN_TICKET_NAME,map.get(Contants.cookies.LOGIN_TICKET_NAME).toString());
            cookie.setPath("/"); // 设置为在同一应用服务器下共享
            if(rememberMe){
                cookie.setMaxAge(3600*24*7);
            }
            response.addCookie(cookie);
            return "redirect:/profile";
        }
        else{
            model.addAttribute("msg",map.get("msg"));
            return "error";
        }
    }

    @RequestMapping(path = "/logout",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String logout(Model model,
                         @CookieValue("ticket") String ticket,
                         HttpServletResponse response){
        userService.logout(ticket);
        return "logout successfully";
    }

    @ExceptionHandler()
    @ResponseBody
    String errorPage(Exception e){
        logger.error("error from loginController",e.getMessage());
        return "error from loginController \n"+e.getMessage();
    }
}
