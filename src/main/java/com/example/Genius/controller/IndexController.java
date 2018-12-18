//package com.example.Genius.controller;
//
//import com.example.Genius.model.Article;
//import com.example.Genius.model.ViewObject;
//import com.example.Genius.service.ArticleService;
//import com.example.Genius.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class IndexController {
//
//    @Autowired
//    private ArticleService articleService;
//
//    @Autowired
//    private UserService userService;
//
//
//    @RequestMapping(value={"/"},method={RequestMethod.GET})
//    String indexPage(Model model){
//        model.addAttribute("vos",getArticles(0,0,10));
//        return "index";
//    }
//
//    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET})         //定义用户超链接进入页面
//    public String userIndex(@PathVariable("userId") int userId,
//                            Model model) {
//        model.addAttribute("vos",getArticles(userId,0,10));
//        return "index";
//    }
//
//    @RequestMapping(value={"/index"},method={RequestMethod.GET})
//    public String index(Model model){
//        model.addAttribute("vos",getArticles(0,0,10));
//        return "index_1";
//    }
//
//
//    public List<ViewObject> getArticles(int articleAuthorId, int offset, int limit) {
//        List<Article> articleList = articleService.selectLatestArticles(articleAuthorId,offset,limit);
//
//        List<ViewObject> vos = new ArrayList<>();
//
//        for(Article article : articleList) {
//            ViewObject vo = new ViewObject();
//            vo.set("article",article);
//            vo.set("user",userService.getUserById(article.getArticleAuthorId()));
//            vos.add(vo);
//        }
//
//        return vos;
//    }
//}
