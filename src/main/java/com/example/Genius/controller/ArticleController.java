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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(path={"/article/add"},method={RequestMethod.POST})
    @ResponseBody
    public String addArticle(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                             @RequestParam(value = "theme",defaultValue = "0") int theme){
        try {

            Article article = new Article();
            article.setArticleContent(content);
            article.setArticleTitle(title);
            article.setLatestUpdateTime(new Date());
            article.setCreateTime(new Date());
            article.setArticleViewCount(0);
            article.setArticleURL("");
            article.setArticleTopicId(theme);
            article.setArticleReplyCount(0);
            article.setArticleLikeCount(0);
            article.setArticleStatus(0);

            if(hostHolder.getCurrentUser() == null) {        // 若未登录，则要求用户做登录处理，此处打算用loginRequiredInterceptor设置拦截

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",1);
                jsonObject.put("msg","用户未登录");                      // 还可以添加一个next跳转参数

                return jsonObject.toJSONString();
            }else {
                article.setArticleAuthorId(hostHolder.getCurrentUser().getoId());
            }
            if(articleService.addArticle(article) > 0) {
                return GeneralUtils.getJSONString(0);          // 发表文章成功
            }
        }catch (Exception e){
            logger.error("发表文章失败" + e.getMessage());
        }

        return GeneralUtils.getJSONString(1,"失败");
    }

    @RequestMapping(path = {"/article/{qid}"},method = {RequestMethod.GET})          // 文章详情页
    @ResponseBody
    public String articleDetail(Model model,
                                @PathVariable("qid") int qid) {

         Article article = articleService.getArticleById(qid);
         User articleUser = userService.getUserById(article.getArticleAuthorId());

         JSONObject jsonObject = new JSONObject();


         jsonObject.put("userNickname",articleUser.getUserNickname());
         jsonObject.put("userProfilePhoto",articleUser.getUserProfilePhoto());

         jsonObject.put("articleTitle",article.getArticleTitle());
         jsonObject.put("articleContent",article.getArticleContent());
         jsonObject.put("articleReplyCount",article.getArticleReplyCount());
         jsonObject.put("articleLikeCount",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
         jsonObject.put("latestUpdateTime", Contants.DATEFORMAT.format(article.getLatestUpdateTime()));

    /*   model.addAttribute("article",article);
         model.addAttribute("user",userService.getUserById(article.getArticleAuthorId()));            */

         List<Comment> commentList = commentService.selectByEntity(qid, EntityType.ENTITY_ARTICLE);    // 选出对文章的评论

        JSONArray jsonArray = new JSONArray();

        for(Comment comment : commentList) {
            JSONObject object = new JSONObject();

             object.put("content",comment.getContent());          // 展示的评论内容
             object.put("createTime",Contants.DATEFORMAT.format(comment.getCreateTime()));
             object.put("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getoId()));  // 评论点赞数
             if(hostHolder.getCurrentUser() == null)
                 object.put("liked",0);
             else
                 object.put("liked",likeService.getLikeStatus(hostHolder.getCurrentUser().getoId(),EntityType.ENTITY_COMMENT,comment.getoId()));

             object.put("commentUserId",userService.getUserById(comment.getUserId()).getoId());
             object.put("commentUserName",userService.getUserById(comment.getUserId()).getUserNickname());
             object.put("commentUserProfilePhoto",userService.getUserById(comment.getUserId()).getUserProfilePhoto());

             jsonArray.add(object);
        }
        jsonObject.put("comment",jsonArray);
        return jsonObject.toJSONString();
    }
}
