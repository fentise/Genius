package com.example.Genius.DAO;

import com.example.Genius.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleDAO {

    String TABLE_NAME = " article ";
    String INSERT_FIELDS = " articleTitle, articleAuthorId, articleContent, articleReplyCount, " +
            "articleLikeCount, articleViewCount, articleTopicId, articleStatus, createTime, latestUpdateTime ";

    String SELECT_FIELDS = " oId," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FIELDS, ") values(#{articleTitle}, #{articleAuthorId}, #{articleContent}, " +
            "#{articleReplyCount}, #{articleLikeCount}, #{articleViewCount}, #{articleTopicId}, " +
            "#{articleStatus}, #{createTime}, #{latestUpdateTime})"})
    @Options(useGeneratedKeys = true,keyColumn = "oId",keyProperty = "oId")
    int addArticle(Article article);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where oId=#{id}"})
    @Options(useGeneratedKeys = true,keyColumn = "oId",keyProperty = "oId")
    Article getArticleById(int id);


    List<Article> selectLatestArticles(@Param("articleAuthorId") int articleAuthorId,@Param("themeId") int themeId,
                                       @Param("offset") int offset, @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set articleReplyCount=#{articleReplyCount} where oId=#{id}"})
    void updateCommentCount(@Param("id") int id, @Param("articleReplyCount") int articleReplyCount);
}
