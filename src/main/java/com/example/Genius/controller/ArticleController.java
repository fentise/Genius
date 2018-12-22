package com.example.Genius.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.*;
import com.example.Genius.service.ArticleService;
import com.example.Genius.service.CommentService;
import com.example.Genius.service.LikeService;
import com.example.Genius.service.UserService;
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
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

//    @Autowired
//    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(path={"/article/add"},method={RequestMethod.POST})
    @ResponseBody
    public String addArticle(@RequestBody Map<String,Object> map) {

        try {

            Article article = new Article();
            article.setArticleContent(map.get("content").toString());
            article.setArticleTitle(map.get("title").toString());
            article.setLatestUpdateTime(new Date());
            article.setCreateTime(new Date());
            article.setArticleViewCount(0);
            article.setArticleTopicId((Integer) map.get("theme"));
            article.setArticleReplyCount(0);
            article.setArticleLikeCount(0);
            article.setArticleStatus(0);
            article.setArticleAuthorId(Integer.parseInt(map.get("userId").toString()));

//            if(hostHolder.getCurrentUser() == null) {        // 若未登录，则要求用户做登录处理，此处打算用loginRequiredInterceptor设置拦截
//
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code",1);
//                jsonObject.put("msg","用户未登录");                      // 还可以添加一个next跳转参数
//
//                return jsonObject.toJSONString();
//            }else {
//                article.setArticleAuthorId(hostHolder.getCurrentUser().getoId());
//            }
            if(articleService.addArticle(article) > 0) {
                return GeneralUtils.getJSONString(0,"成功");          // 发表文章成功
            }
        }catch (Exception e){
            logger.error("发表文章失败" + e.getMessage());
        }
        return GeneralUtils.getJSONString(1,"失败");
    }

    @RequestMapping(path = {"/articleDetail/{articleId}"},method = {RequestMethod.GET,RequestMethod.POST})          // 文章详情页
    @ResponseBody
    public String articleDetail(@RequestBody Map<String,Object> map) {

        int articleId = (Integer) map.get("articleId");

        String userId =  map.get("userId").toString();

        // 更新该篇文章浏览数
        articleService.updateArticleViewCount(articleId,articleService.getArticleById(articleId).getArticleViewCount() + 1);

        Article article = articleService.getArticleById(articleId);
        User articleUser = userService.getUserById(article.getArticleAuthorId());

        JSONObject jsonObject = new JSONObject();

         jsonObject.put("userNickname",articleUser.getUserNickname());
         jsonObject.put("userProfilePhoto",articleUser.getUserProfilePhoto());

         jsonObject.put("articleTitle",article.getArticleTitle());
         jsonObject.put("articleContent",article.getArticleContent());
         jsonObject.put("articleReplyCount",article.getArticleReplyCount());
         jsonObject.put("articleLikeCount",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
         jsonObject.put("latestUpdateTime", Contants.DATEFORMAT.format(article.getLatestUpdateTime()));
         jsonObject.put("articleViewCount",article.getArticleViewCount());

    /*   model.addAttribute("article",article);
         model.addAttribute("user",userService.getUserById(article.getArticleAuthorId()));            */

         List<Comment> commentList = commentService.selectByEntity(articleId, EntityType.ENTITY_ARTICLE);    // 选出对文章的评论

        JSONArray jsonArray = new JSONArray();

        for(Comment comment : commentList) {
            JSONObject object = new JSONObject();

            object.put("commentId",comment.getoId());
             object.put("content",comment.getContent());          // 展示的评论内容
             object.put("createTime",Contants.DATEFORMAT.format(comment.getCreateTime()));
             object.put("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getoId()));  // 评论点赞数
             object.put("replyCount",comment.getCommentReplyCount());
             if(userId == null)
                 object.put("liked",0);
             else
                 object.put("liked",likeService.getLikeStatus(Integer.parseInt(userId),EntityType.ENTITY_COMMENT,comment.getoId()));

             object.put("commentUserId",userService.getUserById(comment.getUserId()).getoId());
             object.put("commentUserName",userService.getUserById(comment.getUserId()).getUserNickname());
             object.put("commentUserProfilePhoto",userService.getUserById(comment.getUserId()).getUserProfilePhoto());

             jsonArray.add(object);
        }
        jsonObject.put("comment",jsonArray);
        return jsonObject.toJSONString();
    }
}
