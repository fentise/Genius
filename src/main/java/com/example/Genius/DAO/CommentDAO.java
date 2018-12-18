package com.example.Genius.DAO;


import com.example.Genius.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface CommentDAO {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " userId, entityId, entityType, content, status, " +
            "createTime, likeCount, commentFloor ";

    String SELECT_FIELDS = " oId," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{entityId},#{entityType},#{content},#{status},#{createTime},#{likeCount}," +
                    "#{commentFloor})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entityId=#{entityId} and entityType=#{entityType} order by oId desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where entityId=#{entityId} and entityType=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId,@Param("entityType") int entityType,
                      @Param("status") int status);

    @Select({"select count(oId) from ", TABLE_NAME, " where entityId=#{entityId} and entityType=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set commentReplyCount=#{commentReplyCount} where commentId=#{commentId}"})
    void updateReplyCount(@Param("commentId") int commentId,@Param("commentReplyCount") int commentReplyCount);


}
