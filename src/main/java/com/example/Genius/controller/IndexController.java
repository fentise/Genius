package com.example.Genius.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.*;
import com.example.Genius.service.ArticleService;
import com.example.Genius.service.CommentService;
import com.example.Genius.service.LikeService;
import com.example.Genius.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ListView;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

//    @Autowired
//    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @RequestMapping(value={"/"},method={RequestMethod.POST})
    @ResponseBody
    public String indexPage(@RequestBody Map<String,Object> map){

        System.out.println("sortType : " + (Integer) map.get("sortType"));
        System.out.println("themeType : " + (Integer)map.get("themeType"));
        System.out.println("userId : " + map.get("userId").toString());

        if(StringUtils.isEmpty(map.get("userId").toString()))
            System.out.println("yes");

        List<ViewObject> vos = getArticles(0,0,100,(Integer) map.get("sortType"),(Integer)map.get("themeType"));         //  获取文章列表

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

//        for(ViewObject vo : vos) {
//            JSONObject object = new JSONObject();
//            object.put("userId",((User) vo.get("user")).getoId());
//            object.put("userNickNAme",((User) vo.get("user")).getUserNickname());
//            object.put("userProfilePhoto",((User) vo.get("user")).getUserProfilePhoto());
//
//            Article article = ((Article) vo.get("article"));
//            object.put("articleId",article.getoId());
//            object.put("articleTitle",article.getArticleTitle());
//            object.put("articleReplyCount",article.getArticleReplyCount());
//            object.put("articleLikeCount",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
//            object.put("latestUpdateTime",Contants.DATEFORMAT.format(article.getLatestUpdateTime()));
//
//            StringBuilder test = new StringBuilder();
//            if(((Article)vo.get("article")).getArticleContent().length() <= 20) {
//                test.append(((Article)vo.get("article")).getArticleContent());
//            }
//            else {
//                test.append(((Article)vo.get("article")).getArticleContent().substring(0,20));
//            }
//            test.append("...");
//            object.put("articleContent",test.toString());
//            jsonArray.add(object);
//        }
//        jsonObject.put("article",jsonArray);
//        return jsonObject.toJSONString();

//        String userId = map.get("userId").toString();            //当前登录用户的id

        if(StringUtils.isEmpty(map.get("userId").toString())) {       //  若当前未登录

            for(ViewObject vo : vos) {
                JSONObject object = new JSONObject();

                JSONObject pub = new JSONObject();

                User user = ((User) vo.get("user"));
                Article article = ((Article) vo.get("article"));

                pub.put("id",article.getoId());
                pub.put("author",user.getUserNickname());
                pub.put("authorId",user.getoId());
                pub.put("avatar",user.getUserProfilePhoto());
                pub.put("title",article.getArticleTitle());

                StringBuilder test = new StringBuilder();

                System.out.println("rawContent : " + article.getArticleRawContent());

                if(!StringUtils.isEmpty(article.getArticleRawContent())){
                    if(article.getArticleRawContent().length() <= 20) {
                        test.append(article.getArticleRawContent());
                    }
                    else {
                        test.append(article.getArticleRawContent().substring(0,20));
                    }
                }
                test.append("...");

                pub.put("content",test.toString());
                pub.put("datetime",Contants.DATEFORMAT.format(article.getLatestUpdateTime()));
                pub.put("likeNum",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
                pub.put("readNum",article.getArticleViewCount());
                pub.put("replyNum",article.getArticleReplyCount());

                object.put("public",pub);

                jsonArray.add(object);
            }
        }
        else{
            for(ViewObject vo : vos) {
                JSONObject object = new JSONObject();

                JSONObject pub = new JSONObject();
                JSONObject action = new JSONObject();

                User user = ((User) vo.get("user"));
                Article article = ((Article) vo.get("article"));

                pub.put("id",article.getoId());
                pub.put("author",user.getUserNickname());
                pub.put("authorId",user.getoId());
                pub.put("avatar",user.getUserProfilePhoto());
                pub.put("title",article.getArticleTitle());

                System.out.println("rawContent : " + article.getArticleRawContent());

                StringBuilder test = new StringBuilder();
                if(!StringUtils.isEmpty(article.getArticleRawContent())){
                    if(article.getArticleRawContent().length() <= 20) {
                        test.append(article.getArticleRawContent());
                    }
                    else {
                        test.append(article.getArticleRawContent().substring(0,20));
                    }
                    test.append("...");
                }
                else{
                    test.append("rawContent为空");
                }

                pub.put("content",test.toString());
                pub.put("datetime",Contants.DATEFORMAT.format(article.getLatestUpdateTime()));
                pub.put("likeNum",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
                pub.put("replyNum",article.getArticleReplyCount());
                pub.put("readNum",article.getArticleViewCount());

                object.put("public",pub);

                /*
                1表示喜欢或回复过， 0 表示 不喜欢
                 */
                action.put("likeThis",likeService.getLikeStatus(Integer.parseInt(map.get("userId").toString()),EntityType.ENTITY_ARTICLE,article.getoId()));
                action.put("repliedThis",commentService.getCommentStatusById(Integer.parseInt(map.get("userId").toString()),article.getoId(),EntityType.ENTITY_ARTICLE));

                object.put("myActions",action);
                jsonArray.add(object);
            }
        }
        jsonObject.put("postCard",jsonArray);
        return jsonObject.toJSONString();
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.POST})         //定义用户超链接进入页面
    @ResponseBody
    public String userIndex(@PathVariable("userId") int userId,
                            @RequestParam(value = "order",defaultValue = "0")int order,
                            @RequestParam(value = "theme",defaultValue = "0")int theme) {
        List<ViewObject> vos = getArticles(userId,0,100,order,theme);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for(ViewObject vo : vos) {
            JSONObject object = new JSONObject();
            object.put("userId",userId);
            object.put("userNickNAme",((User) vo.get("user")).getUserNickname());
            object.put("userProfilePhoto",((User) vo.get("user")).getUserProfilePhoto());

            Article article = ((Article) vo.get("article"));
            object.put("articleId",article.getoId());
            object.put("articleTitle",article.getArticleTitle());
            object.put("articleReplyCount",article.getArticleReplyCount());
            object.put("articleLikeCount",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
            object.put("latestUpdateTime",Contants.DATEFORMAT.format(article.getLatestUpdateTime()));

            StringBuilder test = new StringBuilder();
            if(article.getArticleRawContent().length() <= 20) {
                test.append(article.getArticleRawContent());
            }
            else {
                test.append(article.getArticleRawContent().substring(0,20));
            }
            test.append("...");
            object.put("articleContent",test.toString());
            jsonArray.add(object);
        }
        jsonObject.put("article",jsonArray);
        return jsonObject.toJSONString();
    }


    public List<ViewObject> getArticles(int articleAuthorId, int offset, int limit,int order,int theme) {

        List<Article> articleList = sortArticle(articleService.selectLatestArticles(articleAuthorId,theme,offset,limit),order);

        List<ViewObject> vos = new ArrayList<>();

        for(Article article : articleList) {
            ViewObject vo = new ViewObject();
            vo.set("article",article);
            vo.set("user",userService.getUserById(article.getArticleAuthorId()));
            vos.add(vo);
        }
        return vos;
    }

    public List<Article> sortArticle(List<Article> articleList,int order) {

        if(order == Contants.ORDER.DEFAULT_ORDER) {
            return articleList;                     // 默认按日期排序
        }
        Collections.sort(articleList, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                if(order == Contants.ORDER.COMMENTCOUNT_ORDER)
                    return o2.getArticleReplyCount() - o1.getArticleReplyCount();
                else{
                    return likeService.getLikeCount(EntityType.ENTITY_ARTICLE, o2.getoId()) -  likeService.getLikeCount(EntityType.ENTITY_ARTICLE, o1.getoId());
                }
            }
        });
        return articleList;
    }
}
