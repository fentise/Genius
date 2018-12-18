package com.example.Genius.DAO;

import com.example.Genius.model.UserSubscription;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserSubscriptionDAO {

    @Select("select * from user_subscription where userId = #{userId}")
    List<UserSubscription> queryUserSubscriptionList(@Param("userId") int userId);

    @Insert("insert into user_subscription(userId,targetType,action)values(#{userId},#{targetType},#{action})")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    // 新增一条订阅规则
    void add(UserSubscription userSubscription);

    @Delete("delete from user_subscription where userId = #{userId} and targetType = #{targetType} and action=#{action}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    // 删除一条订阅规则，删除参数内容一致的记录
    void delete(UserSubscription subscription);
}
