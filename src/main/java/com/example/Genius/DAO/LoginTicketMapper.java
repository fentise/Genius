package com.example.Genius.DAO;

import com.example.Genius.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {

    @Insert({"insert into login_ticket(userId,ticket,expired,status)values(#{userId},#{ticket},#{expired},#{status})"})
    int add(LoginTicket loginTicket);

    @Select({"select * from login_ticket where ticket = #{ticket}"})
    LoginTicket selectByTicket(@Param("ticket")String ticket);

    @Update({"update login_ticket set status=#{status} where ticket = #{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
