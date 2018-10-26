package com.example.Genius.DAO;

import com.example.Genius.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface ArticleMapper {

    Logger logger = LoggerFactory.getLogger(ArticleMapper.class);

    @Select("select * from article limit #{limit}")
    public List<Article> selectLatestArticle(@Param("limit") int limit);

    @Insert("inset into article(articleTitle,articleAuthorId,articleContent," +
            "articleURL,articleReplyCount,articleLikeCount,articleViewCount,articleTopicId,articleStatus,createTime,latestUpdateTime)" +
            "value(#{articleTitle},#{articleAuthorId},#{articleContent},#{articleURL},#{articleReplyCount}," +
            "#{articleLikeCount},#{articleViewCount},#{articleTopicId},#{articleStatus},#{createTime},#{latestUpdateTime})")
    public boolean addArticle(Article article);

}
