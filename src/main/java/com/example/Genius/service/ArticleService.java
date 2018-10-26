package com.example.Genius.service;

import com.example.Genius.DAO.ArticleMapper;
import com.example.Genius.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private ArticleMapper articleMapper;
    public List<Article> getLatestArticle(int limit){
        return articleMapper.selectLatestArticle(limit);
    }
}
