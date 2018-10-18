package com.example.Genius.DAO;

import com.example.Genius.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user(userNickname,userPassword,userSalt,userEmail,userHomePageURL,userRole,userStatus,userProfilePhoto)" +
            "value(#{userNickname},#{userPassword},#{userSalt},#{userEmail},#{userHomePageURL},#{userRole},#{userStatus},#{userProfilePhoto})")
    int add(User user);
    @Select("select * from user where userEmail = #{userEmail}")
    User selectByUserEmail(@Param("userEmail") String userEmail );

    @Select("select * from user where oId = #{userId}")
    User selectByUserId(@Param("userId") int userId);

}
