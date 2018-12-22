package com.example.Genius.service;


import com.example.Genius.DAO.ArticleDAO;
import com.example.Genius.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addArticle(Article article) {
        // 过滤用户生成数据中可能出现的html标签，避免导致出现错误
        article.setArticleContent(HtmlUtils.htmlEscape(article.getArticleContent()));
        article.setArticleTitle(HtmlUtils.htmlEscape(article.getArticleTitle()));
        //敏感词过滤
        article.setArticleContent(sensitiveService.filter(article.getArticleContent()));
        article.setArticleTitle(sensitiveService.filter(article.getArticleTitle()));

         return articleDAO.addArticle(article) > 0 ? 1:0;
    }

    public Article getArticleById(int id) {
        return articleDAO.getArticleById(id);
    }

    public List<Article> selectLatestArticles(int articleAuthorId, int themeId, int offset, int limit) {
        return articleDAO.selectLatestArticles(articleAuthorId,themeId,offset,limit);
    }

    public void updateArticleCommentCount(int id,int count) {
        articleDAO.updateCommentCount(id,count);
    }

    public void updateArticleViewCount(int id,int count) {articleDAO.updateViewCount(id,count);}
}
