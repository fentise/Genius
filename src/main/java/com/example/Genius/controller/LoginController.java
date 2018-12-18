package com.example.Genius.controller;

import com.example.Genius.DAO.UserDAO;
import com.example.Genius.service.UserService;
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

    @RequestMapping(path={"/registerAndLogin"})
    public String registerAndLogin(Model model,
                                   @RequestParam(value = "next", required = false) String next){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path={"/register/"},method ={RequestMethod.POST})
    public String register(@RequestParam(value="userNickname",defaultValue="defaultName") String userNickname,
                           @RequestParam(value="userEmail") String userEmail,
                           @RequestParam(value="password") String password,
                           HttpServletResponse response,
                           Model model,
                           @RequestParam(value = "next", required = false) String next){

        try {
            Map<String,Object> map = userService.register(userNickname,userEmail,password);           //调用userService接口进行注册
            if(map.containsKey(LOGIN_TICKET_NAME))          //表明注册成功
            {
                Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
                cookie.setPath("/");        // 设置为在同一应用服务器下共享
                response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页

                if(!StringUtils.isEmpty(next)){
                    return "redirect:" + next;
                }
                return "redirect:/";               //注册成功，用户处于登陆状态，下发ticket, 返回首页。并進行渲染
            }
            else{
                model.addAttribute("msg",map.get("msg").toString());
                return "login";                                 //注册不成功，返回首页，继续注册
            }
        }catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path="/login/",method={RequestMethod.POST})
    public String login(@RequestParam(value = "userEmail") String userEmail,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value="rememberMe",defaultValue = "false") Boolean rememberMe,
                        Model model,
                        HttpServletResponse response,
                        @RequestParam(value = "next", required = false) String next) {

        try{
            Map<String,Object> map = userService.login(userEmail,password,rememberMe);
            if(map.containsKey(LOGIN_TICKET_NAME)) {
                Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
                cookie.setPath("/");        // 设置为在同一应用服务器下共享
                response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页

                if(!StringUtils.isEmpty(next)){
                    return "redirect:" + next;
                }

                return "redirect:/";
            }
            else{
                model.addAttribute("msg",map.get("msg").toString());
                return "login";                                 //登陆不成功，返回首页，继续登陆
            }
        }catch (Exception e){
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/logout",method={RequestMethod.GET})
    public String logout(Model model,
                         @CookieValue(LOGIN_TICKET_NAME) String ticket,            //此参数从客户端发送过来
                         HttpServletResponse response){
        userService.logout(ticket);
        return "redirect:/";
    }

    @ExceptionHandler()
    @ResponseBody
    String errorPage(Exception e){
        logger.error("error from loginController",e.getMessage());
        return "error from loginController \n"+e.getMessage();
    }
}
