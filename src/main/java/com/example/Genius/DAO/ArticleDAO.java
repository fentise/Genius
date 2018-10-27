package com.example.Genius.DAO;

import com.example.Genius.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDAO {

    String TABLE_NAME = " article ";
    String INSERT_FIELDS = " articleTitle, articleAuthorId, articleContent, articleURL, articleReplyCount, " +
            "articleLikeCount, articleViewCount, articleTopicId, articleStatus, createTime, latestUpdateTime ";

    String SELECT_FIELDS = " oId," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FIELDS, ") values(#{articleTitle}, #{articleAuthorId}, #{articleContent}, " +
            "#{articleURL}, #{articleReplyCount}, #{articleLikeCount}, #{articleViewCount}, #{articleTopicId}, " +
            "#{articleStatus}, #{createTime}, #{latestUpdateTime})"})
    int addArticle(Article article);


    List<Article> selectLatestArticles(@Param("articleAuthorId") int articleAuthorId,
                                       @Param("offset") int offset, @Param("limit") int limit);

}
