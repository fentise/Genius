package com.example.Genius.DAO;

import com.example.Genius.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
public interface UserDAO {

    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " userNickname, userPassword, userSalt, userEmail, userHomePageURL, userRole, userStatus, userProfilePhoto";
    String SELECT_FIELDS  = " oId, " + INSERT_FIELDS;

 /*   @Insert("insert into user(userNickname,userPassword,userSalt,userEmail,userHomePageURL,userRole,userStatus,userProfilePhoto)" +
            "value(#{userNickname},#{userPassword},#{userSalt},#{userEmail},#{userHomePageURL},#{userRole},#{userStatus},#{userProfilePhoto})")          */

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS ,
            ") value(#{userNickname},#{userPassword},#{userSalt},#{userEmail},#{userHomePageURL},#{userRole},#{userStatus},#{userProfilePhoto})"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    int add(User user);

    @Select({"select * from user where userEmail = #{userEmail}"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    User selectByUserEmail(@Param("userEmail") String userEmail );

    @Select({"select * from user where oId = #{userId}"})
    @Options(useGeneratedKeys=true, keyProperty="oId", keyColumn="oId")
    User selectByUserId(@Param("userId") int userId);

}
