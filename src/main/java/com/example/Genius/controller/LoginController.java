package com.example.Genius.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.HostHolder;
import com.example.Genius.model.UserNotify;
import com.example.Genius.service.NotifyService;
import com.example.Genius.service.UserService;
import com.example.Genius.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;
//    @Autowired
//    HostHolder hostHolder;

//    @RequestMapping(path={"/registerAndLogin"})
//    @ResponseBody
//    public String registerAndLogin(Model model,
//                                   @RequestParam(value = "next", required = false) String next){
//        model.addAttribute("next",next);             // 跳转参数
//
//        if(!StringUtils.isEmpty(next)) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code",1);
//            jsonObject.put("next",next);
//            return jsonObject.toJSONString();
//        }
//        return GeneralUtils.getJSONString(0);
//    }

    @RequestMapping(path={"/register"},method ={RequestMethod.POST})
    @ResponseBody
    public String register(@RequestBody Map<String,Object> receive) {

        System.out.println("userEmail : " + receive.get("userEmail").toString());
        System.out.println("password : " + receive.get("password").toString());
        System.out.println("userName : " + receive.get("userName").toString());

        Map<String, Object> map = userService.register(receive.get("userName").toString(), receive.get("userEmail").toString(), receive.get("password").toString());

        JSONObject jsonObject = new JSONObject();
        if(map.containsKey("msg")) {          // 表明用户注册不成功
            jsonObject.put("result",0);
            jsonObject.put("message",map.get("msg"));
            System.out.println("msg" + map.get("msg"));
        }
        else{                                               // 表明登录成功
            jsonObject.put("result",1);
            jsonObject.put("message","success");  //注册成功的用户要订阅所有的规则
            int userId = userService.getUserByEmail(receive.get("userEmail").toString()).getoId();
            for(int j = 0; j< Contants.userSubscription.ROLES.size(); j++){
                int[] temp = notifyService.subscriptionReflexInverse(j);
                int targetType = temp[0];
                int action = temp[1];
                notifyService.updateSubscriptionStatus(userId,targetType,action,Contants.userSubscription.SUBSCRIBE);
            }
        }
        return jsonObject.toJSONString();
    }
//        try {
//            Map<String,Object> map = userService.register(userNickname,userEmail,password);           //调用userService接口进行注册
//            if(map.containsKey(LOGIN_TICKET_NAME))          //表明注册成功
//            {
//                Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
//                cookie.setPath("/");        // 设置为在同一应用服务器下共享
//                response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页
//
//                if(!StringUtils.isEmpty(next)){            // 若携带next信息，就同时跳转过去
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("code",0);
//                    jsonObject.put("next",next);
//                    return jsonObject.toJSONString();
//                }
//
//                return GeneralUtils.getJSONString(0);              //注册成功，用户处于登陆状态，下发ticket, 返回首页。并進行渲染
//            }
//            else{
//                return GeneralUtils.getJSONString(1,map.get("msg").toString());            //注册不成功，返回首页，继续注册
//            }
//        }catch (Exception e) {
//            logger.error("注册异常" + e.getMessage());
//            return GeneralUtils.getJSONString(1,"注册异常");
//        }

    @RequestMapping(path="/login",method={RequestMethod.POST})
    @ResponseBody
    public String login(@RequestBody Map<String,Object> receive) {

        System.out.println("userEmail : " + receive.get("userEmail").toString());
        System.out.println("password : " + receive.get("password").toString());

        Map<String,Object> map = userService.login(receive.get("userEmail").toString(),receive.get("password").toString());

        JSONObject jsonObject = new JSONObject();
        if(map.containsKey("msg")) {          // 表明用户登录不成功
            jsonObject.put("userId","");
            jsonObject.put("result",0);
            jsonObject.put("message",map.get("msg"));
            System.out.println("msg" + map.get("msg"));
        }
        else{                                               // 表明登录成功
            int userId =(Integer) map.get("userId");
            jsonObject.put("userId",map.get("userId").toString());
            jsonObject.put("result",1);
            jsonObject.put("message","success");
            jsonObject.put("userName",userService.getUserById(userId).getUserNickname());    // 返回名字
        }
        return jsonObject.toJSONString();
    }
//        try{
//            System.out.println("userEmail : " + receive.get("userEmail").toString());
//            System.out.println("password : " + receive.get("password").toString());
//            System.out.println("rememberMe : " + (Boolean)receive.get("rememberMe"));
//            Map<String,Object> map = userService.login(receive.get("userEmail").toString(),receive.get("password").toString(),(Boolean)receive.get("rememberMe"));
//            if(map.containsKey(LOGIN_TICKET_NAME)) {
//                Cookie cookie = new Cookie(LOGIN_TICKET_NAME,map.get(LOGIN_TICKET_NAME).toString());
//                cookie.setPath("/");        // 设置为在同一应用服务器下共享
//                response.addCookie(cookie);         //登陆成功，用户处于登陆状态，下发ticket, 返回首页
//
//                if(!StringUtils.isEmpty(receive.get("next").toString())){      // 如果next不为空
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("code",0);
//                    jsonObject.put("next",receive.get("next").toString());
//                    return jsonObject.toJSONString();
//                }
//                return  GeneralUtils.getJSONString(0);            // 0表示登录成功
//            }
//            else{
//                return GeneralUtils.getJSONString(1,map.get("msg").toString());       // 登录失败，返回错误信息
//            }
//        }catch (Exception e){
//            logger.error("登录异常" + e.getMessage());
//            return GeneralUtils.getJSONString(1,"登录异常");
//        }


//    @RequestMapping(path = "/logout",method={RequestMethod.GET})            // 退出登陆状态
//    @ResponseBody
//    public String logout(@CookieValue(LOGIN_TICKET_NAME) String ticket,            //此参数从客户端发送过来
//                         HttpServletResponse response){
//        userService.logout(ticket);
//        return GeneralUtils.getJSONString(0);
//    }

    @ExceptionHandler()
    @ResponseBody
    String errorPage(Exception e){
        logger.error("error from loginController",e.getMessage());
        return "error from loginController \n"+e.getMessage();
    }
}