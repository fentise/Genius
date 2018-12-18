package com.example.Genius;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Genius.controller.IndexController;
import com.example.Genius.model.Article;
import com.example.Genius.model.ViewObject;
import com.example.Genius.service.ArticleService;
import com.example.Genius.utils.GeneralUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonUtilTest {

    @Autowired
    ArticleService articleService;

    public static void print(int index,Object obj) {
        System.out.println(String.format("%d, %s",index, obj.toString()));
    }

    @Test
    public void contextLoads() {

        List<Article> articleList = articleService.selectLatestArticles(0,0,0,10);

        JSONArray jsonArray = new JSONArray();

        for(Article article : articleList) {
            jsonArray.add(article);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","邮箱格式非法");
        jsonObject.put("is","1");
        jsonObject.put("article",jsonArray);

        print(1,jsonObject);
    }
}
