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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class ReplyController {

    @Autowired
    HostHolder hostHolder;         // 查看当前登录用户

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

    /**
     *
     * @param targetId : 对方用户的id
     * @param content : 回复内容
     * @return
     */
    @RequestMapping(path = {"/addReply"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addReply(@RequestParam("targetId") int targetId,
                             @RequestParam("commentId") int commentId,
                             @RequestParam("content") String content){
//        try{
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
            reply.setUserId(Contants.TEST_GLOBAL_USER_ID);
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
            Reminder reminder = new Reminder(reply.getUserId(),reply.getCommentId(),Contants.reminder.TARGET_TYPE_COMMENT,Contants.reminder.ACTION_REPLY,reply.getCreateTime());
            notifyService.createNotify(reminder);

//        }catch (Exception e) {
//            logger.error("增加回复失败" + e.getMessage());
//        }
        return GeneralUtils.getJSONString(0);
    }

    @RequestMapping(path = {"/showReply"},method = {RequestMethod.POST})
    @ResponseBody
    public String showReply(@RequestParam("commentId") int commentId) {

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
