package com.example.Genius.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.*;
import com.example.Genius.service.*;
import com.example.Genius.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ReplyController {

//    @Autowired
//    HostHolder hostHolder;         // 查看当前登录用户

    @Autowired
    ReplyService replyService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;
    @Autowired
    private NotifyService notifyService;
    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    @RequestMapping(path = {"/addReply"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addReply(@RequestBody Map<String,Object> map){
        try{

            String userId = map.get("userId").toString();

            String content = map.get("content").toString();

            int targetId = (Integer) map.get("targetId");

            int commentId = (Integer)map.get("commentId");

            Reply reply = new Reply();

//            if(hostHolder.getCurrentUser() != null) {  // 判断当前是否有登录的用户，设置评论的发布者
//                reply.setUserId(hostHolder.getCurrentUser().getoId());
//            }else {
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("code",1);
//                jsonObject.put("msg","用户未登录");          // 同样，此处可以做登录跳转
//
//                return jsonObject.toJSONString();
//            }

            reply.setUserId(Integer.parseInt(userId));
            reply.setReplyContent(content);
            reply.setLikeCount(0);
            reply.setCreateTime(new Date());
            reply.setStatus(0);
            reply.setTargetId(targetId);
            reply.setCommentId(commentId);

            replyService.addReply(reply);

            int count = replyService.getReplyCount(commentId); // 添加回复后，同步更新评论回复数目
            commentService.updateCommentReplyCount(commentId,count);

            // 新增回复的同时，增加对评论发布者的提醒
            Reminder reminder = new Reminder(Integer.parseInt(userId),reply.getCommentId(),Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_REPLY,reply.getCreateTime());
            notifyService.createNotify(reminder);

            return GeneralUtils.getJSONString(0,"成功");
        }catch (Exception e) {
            logger.error("增加回复失败" + e.getMessage());
        }
        return GeneralUtils.getJSONString(0,"出现异常");
    }

    @RequestMapping(path = {"/showReply"},method = {RequestMethod.POST})
    @ResponseBody
    public String showReply(@RequestBody Map<String,Object> map) {

        int commentId = (Integer) map.get("commentId");

        List<Reply> replyList = replyService.selectBycommentID(commentId);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for(Reply reply : replyList) {
            JSONObject object = new JSONObject();
            object.put("oId",reply.getoId());
            object.put("content",reply.getReplyContent());

            User user = userService.getUserById(reply.getUserId());
            User targetUser = userService.getUserById(reply.getTargetId());
            object.put("userId",user.getoId());
            object.put("userName",user.getUserNickname());
            object.put("targetUserId",targetUser.getoId());
            object.put("targetUserName",targetUser.getUserNickname());

            object.put("replyTime", Contants.DATEFORMAT.format(reply.getCreateTime()));
            object.put("likeCount",likeService.getLikeCount(EntityType.ENTITY_REPLY,reply.getoId()));
            jsonArray.add(object);
        }
        jsonObject.put("reply",jsonArray);
        return jsonObject.toJSONString();
    }
}
