package com.example.Genius.DAO;

import com.example.Genius.Contants.Contants;
import com.example.Genius.model.Message;
import com.example.Genius.model.Reminder;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReminderDAO {
    @Insert({"insert into reminder(senderId,targetId,targetType,action,createTime,status)values(#{senderId},#{targetId},#{targetType},#{action},#{createTime},#{status})"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    void add(Reminder reminder);

    @Select("select * from reminder where oId = #{oId}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    Reminder queryReminder(@Param("oId") int oId);

//    @Select("select * from reminder where order by createTime desc limit #{limit}")
//    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
//    List<Reminder> queryReminderList(@Param("userId") int userId,@Param("limit") int limit);
//
//    @Select("select * from reminder where createTime > #{time} order by createTime desc limit #{limit}")
//    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
//    List<Reminder> queryReminderListAfterTime(@Param("time")Date time,@Param("limit") int limit);


    @Select("select article_reminder.*  " +
            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_ARTICLE+"}) as article_reminder inner join (select * from article where articleAuthorId = #{userId}) as user_article" +
            "on article_reminder.targetId = user_article.oId " +
            "where article_reminder.createTime > #{time} "+
            "order by article_reminder.createTime desc" +
            "limit #{limit}")
    List<Reminder> queryRemindersRelateToArticleAfterTime(@Param("userId") int userId,@Param("time") Date time,@Param("limit") int limit);

    @Select("select article_reminder.*  " +
            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_ARTICLE+"}) as article_reminder inner join (select * from article where articleAuthorId = #{userId}) as user_article " +
            "on article_reminder.targetId = user_article.oId " +
            "order by article_reminder.createTime desc " +  //分为多行写的sql语句，拼接时记得加空格，不然粘在一起时无法识别关键字的，删除换行符之后也要记得加空格
            "limit #{limit}")
    List<Reminder> queryRemindersRelateToArticle(@Param("userId") int userId,@Param("limit") int limit);

    @Select("select article_reminder.*  " +
            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_REPLY+"}) as article_reminder inner join (select * from reply where replyAuthorId = #{userId}) as user_reply " +
            "on article_reminder.targetId = user_reply.oId " +
            "order by user_reply.createTime desc " +  //分为多行写的sql语句，拼接时记得加空格，不然粘在一起时无法识别关键字的，删除换行符之后也要记得加空格
            "limit #{limit}")
    List<Reminder> queryRemindersRelateToReply(@Param("userId") int userId,@Param("limit") int limit);

    @Select("select article_reminder.*  " +
            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_REPLY+"}) as article_reminder inner join (select * from reply where replyAuthorId = #{userId}) as user_reply " +
            "on article_reminder.targetId = user_reply.oId " +
            "where user_reply.createTime > #{time} "+
            "order by user_reply.createTime desc " +  //分为多行写的sql语句，拼接时记得加空格，不然粘在一起时无法识别关键字的，删除换行符之后也要记得加空格
            "limit #{limit}")
    List<Reminder> queryRemindersRelateToReplyAfterTime(@Param("userId") int userId,@Param("time") Date time,@Param("limit") int limit);

//    @Select("select article_reminder.*  " +
//            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_COMMENT+"}) as article_reminder inner join (select * from article where articleAuthorId = #{userId}) as user_article " +
//            "on article_reminder.targetId = user_article.oId " +
//            "order by article_reminder.createTime desc " +  //分为多行写的sql语句，拼接时记得加空格，不然粘在一起时无法识别关键字的，删除换行符之后也要记得加空格
//            "limit #{limit}")
//    List<Reminder> queryRemindersRelateToComment(@Param("userId") int userId,@Param("limit") int limit);
//
//    @Select("select article_reminder.*  " +
//            "from (select * from reminder  where targetType = ${"+ Contants.reminder.TARGET_TYPE_ARTICLE+"}) as article_reminder inner join (select * from article where articleAuthorId = #{userId}) as user_article " +
//            "on article_reminder.targetId = user_article.oId " +
//            "order by article_reminder.createTime desc " +  //分为多行写的sql语句，拼接时记得加空格，不然粘在一起时无法识别关键字的，删除换行符之后也要记得加空格
//            "limit #{limit}")
//    List<Reminder> queryRemindersRelateToArticle(@Param("userId") int userId,@Param("limit") int limit);

}
