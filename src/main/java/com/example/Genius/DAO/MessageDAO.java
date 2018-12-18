package com.example.Genius.DAO;

import com.example.Genius.model.Announce;
import com.example.Genius.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageDAO {
    @Insert({"insert into message(senderId,messageContent,createTime,status,receiverId,sessionId)values(#{senderId},#{messageContent},#{createTime},#{status},#{receiverId},#{sessionId})"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    void add(Message message);

    @Select("select * from message where oId = #{oId}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    Message queryMessage(@Param("oId") int oId);

}
