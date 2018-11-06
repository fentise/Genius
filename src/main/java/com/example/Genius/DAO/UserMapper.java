package com.example.Genius.DAO;

import com.example.Genius.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {

    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " userNickname, userPassword, userSalt, userEmail, userHomePageURL, userRole, userStatus, userProfilePhoto";
    String SELECT_FIELDS  = " oId, " + INSERT_FIELDS;

 /*   @Insert("insert into user(userNickname,userPassword,userSalt,userEmail,userHomePageURL,userRole,userStatus,userProfilePhoto)" +
            "value(#{userNickname},#{userPassword},#{userSalt},#{userEmail},#{userHomePageURL},#{userRole},#{userStatus},#{userProfilePhoto})")          */

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS ,
            ") value(#{userNickname},#{userPassword},#{userSalt},#{userEmail},#{userHomePageURL},#{userRole},#{userStatus},#{userProfilePhoto})"})
    int add(User user);

    @Select({"select * from user where userEmail = #{userEmail}"})
    User selectByUserEmail(@Param("userEmail") String userEmail );

    @Select({"select * from user where oId = #{userId}"})
    User selectByUserId(@Param("userId") int userId);

}
