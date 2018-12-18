package com.example.Genius.DAO;

import com.example.Genius.Contants.Contants;
import com.example.Genius.model.Announce;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AnnounceDAO {

    @Insert({"insert into announce(announceContent,createTime,targetType,status)values(#{announceContent},#{createTime},#{targetType},#{status})"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    void add(Announce announce);
    @Select("select * from announce where oId = #{oId}")
    Announce queryAnnounce(@Param("oId") int oId);

    @Select("select * from announce  order by createTime desc limit #{limit}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    List<Announce> queryAnnounceList(@Param("limit") int limit);


    @Select("select * from announce where createTime > #{time} order by createTime desc limit #{limit}")
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    List<Announce> queryAnnounceListAfterTime(@Param("time")Date time,@Param("limit") int limit);


}
