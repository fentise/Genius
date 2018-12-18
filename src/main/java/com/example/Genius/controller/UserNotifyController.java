package com.example.Genius.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.DAO.UserNotifyDAO;
import com.example.Genius.model.Announce;
import com.example.Genius.model.Message;
import com.example.Genius.model.Reminder;
import com.example.Genius.service.NotifyService;
import com.example.Genius.utils.GeneralUtils;
import com.example.Genius.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserNotifyController {
    /**
     * @Description: 用户消息处理中心
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private NotifyService notifyService;

    /**-----------------------------获取用户消息部分-----------------------------------*/
    /**
     * @Description: 获取指定用户的最新消息列表 各类消息前50条
     * @param: userId
     * @return: java.lang.String
     */
    @RequestMapping("/userNotify/{userId}")
    @ResponseBody
    public String getUserNotifyList(@PathVariable("userId") int userId){
        HashMap<Integer,Announce> announceHashMap = new HashMap<>(); // user_notify.oId:Announce   user_notify.oId 用于设置readStatus
        HashMap<Integer,Reminder> reminderHashMap = new HashMap<>(); // user_notify.oId:Reminder
        HashMap<Integer,Message> messageHashMap = new HashMap<>();  // user_notify.oId:Message
        HashMap<Integer,Integer> readStatusMap = new HashMap<>();  // user_notify.oId:readStatus
        notifyService.getLatestNotify(userId,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
        HashMap<String,Object> result = new HashMap<>();
        result.put("announces",announceHashMap);
        result.put("reminders",reminderHashMap);
        result.put("messages",messageHashMap);
        result.put("readStatusList",readStatusMap);
        //logger.error(JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }

    /**
     * @Description: 用户拉取公告和提醒，并将新消息都返回，用于用户定时拉取消息，而非实时刷新消息 ---定时任务交给前端
     * @param: userId
     * @param: time 前端中最新一条userNotify的createTime
     * @return: java.lang.String
     */
    @RequestMapping(value="userNotify/pull/{userId}")
    @ResponseBody
    public String pullNotify(@PathVariable("userId") int userId,@RequestParam("time") String timeString)throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(timeString);
        logger.error(sdf.format(time));
        notifyService.pullReminder(userId);
        notifyService.pullAnnounce(userId);
        HashMap<Integer,Announce> announceHashMap = new HashMap<>();
        HashMap<Integer,Reminder> reminderHashMap = new HashMap<>();
        HashMap<Integer,Message> messageHashMap = new HashMap<>();
        HashMap<Integer,Integer> readStatusMap = new HashMap<>();
        notifyService.getNotifyAfterTime(userId,time,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
        HashMap<String,Object> result = new HashMap<>();
        result.put("announces",announceHashMap);
        result.put("reminders",reminderHashMap);
        result.put("messages",messageHashMap);
        result.put("readStatusList",readStatusMap);

        return JSON.toJSONString(result);
    }

    /**
     * @Description: 用户查询是否有新的消息，用于实时刷新消息  ---定时任务交给前端
     * @param: userId
     * @param: time
     * @return: java.lang.String
     */
    @RequestMapping(value="userNotify/refresh/{userId}")
    @ResponseBody
    public String refreshNotify(@PathVariable("userId") int userId,@RequestParam("time")String timeString)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(timeString);
        HashMap<Integer,Announce> announceHashMap = new HashMap<>();
        HashMap<Integer,Reminder> reminderHashMap = new HashMap<>();
        HashMap<Integer,Message> messageHashMap = new HashMap<>();
        HashMap<Integer,Integer> readStatusMap = new HashMap<>();
        notifyService.getNotifyAfterTime(userId,time,announceHashMap,reminderHashMap,messageHashMap,readStatusMap);
        HashMap<String,Object> result = new HashMap<>();
        result.put("announces",announceHashMap);
        result.put("reminders",reminderHashMap);
        result.put("messages",messageHashMap);
        result.put("readStatusList",readStatusMap);
        return JSON.toJSONString(result);
    }
    /**
     * @Description: 获取指定时间之前的更多的提醒类型的历史消息
     * @param: userId
     * @param: time
     * @param: limit
 * @return: java.lang.String
     */
    @RequestMapping(value="userNotify/getReminder/{userId}/{time}/{limit}")
    public String getMoreReminder(@PathVariable("userId")int userId, @PathVariable("time")Date time,@PathVariable("limit") int limit){
        return "";
    }
    @RequestMapping(value="userNotify/getAnnounce/{time}/{limit}")
    public String getMoreAnnounce(@PathVariable("userId")int userId, @PathVariable("time")Date time,@PathVariable("limit") int limit){
        return "";
    }
    @RequestMapping(value="userNotify/getMessage/{time}/{limit}")
    public String getMoreMessage(@PathVariable("userId")int userId, @PathVariable("time")Date time,@PathVariable("limit") int limit){
        return "";
    }

    /**
     * @Description: 设置指定用户的某条消息为已读
     * @param: userNotifyId
     */
    @RequestMapping(value="userNotify/setRead/{userNotifyId}",method = {RequestMethod.POST})
    @ResponseBody
    public String setRead(@PathVariable("userNotifyId") int userNotifyId){
        notifyService.readUserNotify(userNotifyId);
        return "success";
    }
    /**------------------------发布消息部分--------------------------*/
    @RequestMapping(value="userNotify/createNotify/announce")
    @ResponseBody
    public String createAnnounce(@RequestBody HashMap<String,String> notify)throws ParseException{
        Announce announce = new Announce();
        int code = 1;
        List<String> msg = new ArrayList<>();
        if(!notify.containsKey("announceContent")){
            msg.add("announceContent should not be empty");
            code = 0;
        }
        if(!notify.containsKey("createTime")){
            msg.add("createTime should not be empty");
            code = 0;
        }
        if (code==0){
            return JsonUtils.getJSONString(code,"fail");
        }
        announce.setAnnounceContent(notify.get("announceContent"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(notify.get("createTime"));
        logger.error(time.toString());
        announce.setCreateTime(time);
        announce.setStatus(0);
        announce.setTargetType(0);
        logger.error(notify.get("createTime"));
        logger.error(announce.getCreateTime().toString());
        notifyService.createNotify(announce);
        return JsonUtils.getJSONString(code,"success");
    }
    @RequestMapping(value="userNotify/createNotify/message")
    @ResponseBody
    public String createMessage(@RequestBody HashMap<String,String> notify)throws ParseException{
        Message message = new Message();
        // 值检查
        int code = 1;
        List<String> msg = new ArrayList<>();
        if( !notify.containsKey("senderId")){
            code = 0;
            msg.add("senderId should not empty");
        }

        if(!notify.containsKey("receiverId")){
            code = 0;
            msg.add("senderId should not empty");
        }


        if (code == 0){
            return JsonUtils.getJSONString(code,"fail"); //TODO:看情况增加错误信息
        }
        if(!notify.containsKey("sessionId")){
            message.setSessionId(notifyService.getSessionId(Integer.parseInt(notify.get("senderId")),Integer.parseInt(notify.get("receiverId"))));
        }
        else{
            message.setSessionId(Integer.parseInt(notify.get("sessionId")));
        }
        message.setSenderId(Integer.parseInt(notify.get("senderId")));
        message.setReceiverId(Integer.parseInt(notify.get("receiverId")));
        message.setMessageContent(notify.get("messageContent"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(notify.get("createTime"));
        message.setCreateTime(time);
        notifyService.createNotify(message);
        return JsonUtils.getJSONString(code,"success");
    }

    /**------------------------订阅规则部分-----------------------------*/
    /**
     * @Description: 获取指定用户的订阅规则
     * @param: userId
     * @return: java.lang.String
     */
    @RequestMapping(value="userNotify/userSubscription/{userId}",method = {RequestMethod.GET})
    @ResponseBody
    public String getUserSubscription(@PathVariable("userId") int userId){
        HashMap<Integer,HashMap<Integer,Integer>> subMap = notifyService.getUserSubscription(userId);
        int[] subReflex = notifyService.subscriptionReflex(subMap);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i<Contants.userSubscription.ROLES.size();i++){
            JSONObject json = new JSONObject();
            json.put("id",i);
            json.put("content",Contants.userSubscription.ROLES.get(i));
            json.put("subscriptionStatus",subReflex[i]);
            jsonArray.add(json);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("notice",jsonArray);
        return jsonObject.toJSONString();
    }
    /**
     * @Description: 更新指定用户的订阅规则
     * @param: userId
     * @param: subscriptionUpdate
     * @return: java.lang.String
     */
    @RequestMapping(value="userNotify/userSubscription/update/{userId}",method = {RequestMethod.POST})
    @ResponseBody
    public String updateUserSubscription(@PathVariable("userId") int userId,@RequestBody HashMap<Integer,Integer> subscriptionUpdate){
        for(HashMap.Entry<Integer,Integer> entry:subscriptionUpdate.entrySet()){
            int[] temp = notifyService.subscriptionReflexInverse(entry.getKey());
            int targetType = temp[0];
            int action = temp[1];
            notifyService.updateSubscriptionStatus(userId,targetType,action,entry.getValue());
        }
        return "success";
    }



}
