package com.example.Genius.service;


import com.example.Genius.DAO.ArticleDAO;
import com.example.Genius.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleDAO articleDAO;

    public List<Article> selectLatestArticles(int articleAuthorId, int offset, int limit) {

        return articleDAO.selectLatestArticles(articleAuthorId,offset,limit);
    }
}
