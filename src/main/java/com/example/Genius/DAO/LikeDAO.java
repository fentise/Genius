package com.example.Genius.DAO;

import com.example.Genius.model.UserLike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LikeDAO {

    String TABLE_NAME = " user_like ";
    String INSERT_FIELDS = " userId, entityId, entityType, createTime, status ";

    String SELECT_FIELDS = " oId," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{entityId},#{entityType},#{createTime},#{status})"})
    int like(UserLike userLike);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where userId=#{userId} order by oId desc"})
    List<UserLike> selectByUserId(@Param("userId") int userId);

    @Select({"select status from ", TABLE_NAME, " where userId=#{userId} and entityId=#{entityId} and entityType=#{entityType}"})
    List<Integer> getStatus(@Param("userId") int userId,@Param("entityId") int entityId,@Param("entityType") int entityType);

    /**
     *
     * @param entityId
     * @param entityType
     * @param status : 0表示点赞状态，1表示取消点赞
     */
    @Update({"update ",TABLE_NAME," set status=#{status} where userId=#{userId} and entityId=#{entityId} and entityType=#{entityType}"})
    void updateStatus(@Param("userId") int userId,@Param("entityId") int entityId, @Param("entityType") int entityType,
                      @Param("status") int status);

    @Select({"select count(oId) from ", TABLE_NAME, " where entityId=#{entityId} and entityType=#{entityType} and status=#{status}"})
    int getLikeCountByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType,
                             @Param("status") int status);
}
