package com.example.Genius.DAO;

import com.example.Genius.model.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplyDAO {

    String TABLE_NAME = " reply ";
    String INSERT_FIELDS = " userId, targetId, replyContent, commentId, likeCount, status, createTime ";

    String SELECT_FIELDS = " oId," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{targetId},#{replyContent},#{commentId},#{likeCount},#{status},#{createTime})"})
    int addReply(Reply reply);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where commentId=#{commentId} order by oId desc"})
    List<Reply> selectByCommentId(@Param("commentId") int commentId);

    @Select("select * from reply where oId = #{oId}")
    Reply queryRely(@Param("oId") int oId);

 /*   @Update({"update ",TABLE_NAME," set status=#{status} where entityId=#{entityId} and entityType=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType,
                      @Param("status") int status);                */

    @Select({"select count(oId) from ", TABLE_NAME, " where commentId=#{commentId}"})
    int getReplyCount(@Param("commentId") int commentId);
}
