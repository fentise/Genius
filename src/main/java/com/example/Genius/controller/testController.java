package com.example.Genius.controller;

import com.example.Genius.model.Article;
import com.example.Genius.model.ViewObject;
import com.example.Genius.service.ArticleService;
import com.example.Genius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class testController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @RequestMapping("/test")
    String test(Model model){
        List<String> stringList = new ArrayList<>();

        for(int i = 0;i < 10;++ i) {
            stringList.add(String.format("String %d",i));
        }

        List<Map> mapList = new ArrayList<>();

        for(int i = 0;i < 10;++ i) {
            Map<String,Object> map = new HashMap<>();
            map.put("key1",i);
            map.put("key2",i * i);
            mapList.add(map);
        }

        List<Article> articleList = articleService.selectLatestArticles(0,0,10);

        System.out.print(articleList);

        List<ViewObject> vos = new ArrayList<>();

        for(Article article : articleList) {
            ViewObject vo = new ViewObject();                                //特别注意，vo是一个对象，并不是一个map
            vo.set("article",article);
            vo.set("user",userService.getUserById(article.getArticleAuthorId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        model.addAttribute("stringList",stringList);
        model.addAttribute("mapList",mapList);
        model.addAttribute("articleList",articleList);
        return "test";
    }

    @RequestMapping("/testHeader")
    String testHeader(){
        return "header_test";
    }
}
