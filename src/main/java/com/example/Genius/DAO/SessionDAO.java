package com.example.Genius.DAO;

import com.example.Genius.model.Session;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SessionDAO {

    @Insert("insert into session(userId1,userId2)values(#{userId1},#{userId2})")
    @Options(useGeneratedKeys = true,keyProperty = "oId",keyColumn = "oId")
    void add(Session session);

    @Select("select * from session where (userId1=#{userId1} and userId2=#{userId2}) or (userId1=#{userId2} and userId2=#{userId1})")
    Session querySession(@Param("userId1") int userId1,@Param("userId2") int userId2);
}
