package com.example.Genius.DAO;

import com.example.Genius.Contants.Contants;
import com.example.Genius.model.Message;
import com.example.Genius.model.UserNotify;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface UserNotifyDAO {
    @Insert({"insert into user_notify(userId,createTime,hasRead,notifyId,notifyType)values(#{userId},#{createTime},#{hasRead},#{notifyId},#{notifyType})"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    void add(UserNotify userNotify);

//    // 切记sql中判断数据相等采用一个等号“=”
//    @Select("select * from user_notify where userId = #{userId} order by createTime desc limit #{limit}")
//    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
//    List<UserNotify> queryUserNotifyList(@Param("userId") int userId,@Param("limit") int limit);

    @Select("select * from user_notify where userId = #{userId} and notifyType=#{type} order by createTime limit 1")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    UserNotify queryLatestNotifyWithType(@Param("userId") int userId,@Param("type") int type);

    @Select("select * from user_notify where userId = #{userId} and notifyType=#{type} order by createTime limit #{limit}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    List<UserNotify> queryLatestNotifyListWithType(@Param("userId") int userId,@Param("type") int type,@Param("limit") int limit);

    @Select("select * from user_notify where userId = #{userId} and notifyType=#{type} and createTime > #{time} order by createTime limit #{limit}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    List<UserNotify> queryLatestNotifyListWithTypeAfterTime(@Param("userId") int userId,@Param("type") int type,@Param("limit") int limit,@Param("time") Date time);

//    @Update("update user_notify set hasRead = #{readStatus} where oId = #{oId}")
//    void setReadStatus(@Param("oId") int oId,@Param("readStatus") int readStatus);

    @Update("update user_notify set hasRead = ${"+Contants.userNotify.REAND+"} where hasRead = ${"+Contants.userNotify.UNREAD+"} "+
            "and createTime <= (select createTime from ( select * from user_notify where oId = #{oId})as temp ) ")
    //将oId所指示的记录以及之前的未读消息都置为已读
    void setHasRead(@Param("oId") int oId);

    @Select("select user_notify.* " + //查出用户的每个对话的前50条记录 FIXME:会将全部记录查出，无法限定数目
            "from message inner join user_notify on message.oId = user_notify.notifyId " +
            "where user_notify.userId = #{userId} and user_notify.notifyType = ${"+Contants.userNotify.TYPE_MESSAGE +"} " +
            "and ( select count(distinct(m.createTime)) from message as m where m.sessionId = message.sessionId and m.createTime > message.createTime ) < #{limit} " +
            "order by sessionId ")
    List<UserNotify> queryMessageNotifyListOfSession(@Param("userId") int userId,@Param("limit") int limit);

    @Select("select user_notify.* " + //查出用户的每个对话的前50条记录 FIXME:会将全部记录查出，无法限定数目
            "from message inner join user_notify on message.oId = user_notify.notifyId " +
            "where user_notify.userId = #{userId} and user_notify.notifyType = ${"+Contants.userNotify.TYPE_MESSAGE +"} " +
            "and ( select count(distinct(m.createTime)) from message as m where m.sessionId = message.sessionId and m.createTime > message.createTime ) < #{limit} " +
            "and user_notify.createTime > #{time} "+
            "order by sessionId ")
    List<UserNotify> queryMessageNotifyListOfSessionAfterTime(@Param("userId") int userId,@Param("limit") int limit,@Param("time") Date time);

}
