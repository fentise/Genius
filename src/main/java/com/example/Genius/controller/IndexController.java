package com.example.Genius.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.Contants.Contants;
import com.example.Genius.model.Article;
import com.example.Genius.model.EntityType;
import com.example.Genius.model.User;
import com.example.Genius.model.ViewObject;
import com.example.Genius.service.ArticleService;
import com.example.Genius.service.LikeService;
import com.example.Genius.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    /**
     *
     * @param order ： 排序方式，默认为0，表示按时间排序
     * @param theme ： 文章主题，默认为0，表示不分区
     * @return
     */
    @RequestMapping(value={"/"},method={RequestMethod.POST})
    @ResponseBody
    String indexPage(@RequestParam(value = "order",defaultValue = "0") int order,
                     @RequestParam(value = "theme",defaultValue = "0") int theme){

        List<ViewObject> vos = getArticles(0,0,100,order,theme);         //  获取文章列表

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for(ViewObject vo : vos) {
            JSONObject object = new JSONObject();
            object.put("userId",((User) vo.get("user")).getoId());
            object.put("userNickNAme",((User) vo.get("user")).getUserNickname());
            object.put("userProfilePhoto",((User) vo.get("user")).getUserProfilePhoto());

            Article article = ((Article) vo.get("article"));
            object.put("articleId",article.getoId());
            object.put("articleTitle",article.getArticleTitle());
            object.put("articleReplyCount",article.getArticleReplyCount());
            object.put("articleLikeCount",likeService.getLikeCount(EntityType.ENTITY_ARTICLE,article.getoId()));
            object.put("latestUpdateTime",Contants.DATEFORMAT.format(article.getLatestUpdateTime()));

            StringBuilder test = new StringBuilder();
            if(((Article)vo.get("article")).getArticleContent().length() <= 20) {
                test.append(((Article)vo.get("article")).getArticleContent());
            }
            else {
                test.append(((Article)vo.get("article")).getArticleContent().substring(0,20));
            }
            test.append("...");
            object.put("articleContent",test.toString());
            jsonArray.add(object);
        }
        jsonObject.put("article",jsonArray);
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
            if(((Article)vo.get("article")).getArticleContent().length() <= 20) {
                test.append(((Article)vo.get("article")).getArticleContent());
            }
            else {
                test.append(((Article)vo.get("article")).getArticleContent().substring(0,20));
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
