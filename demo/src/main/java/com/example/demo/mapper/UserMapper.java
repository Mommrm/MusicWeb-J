package com.example.demo.mapper;


import cn.hutool.core.date.DateTime;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.PlayList;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper{
    @Select("Select music_user_phone from music_user_info where music_user_phone = #{phone}")
    String selectByPhone(String phone);

    @Select("Select music_user_email from music_user_info where music_user_email = #{email}")
    String selectByEmail(String email);

    @Insert("Insert into music_user_info(music_user_id,music_user_name,music_user_phone,music_user_email,music_user_regisdate) values (#{userId},#{userName},#{phone}, #{email},#{now})")
    Boolean createUser(String userId,String userName ,String phone, String email, DateTime now);

    @Select("Select * from music_user_info where music_user_id = #{userId}")
    User selectByUserId(String userId);

    @Update("Update music_user_info set music_user_name = #{userName}, music_user_avatar = #{userAvatar} , music_user_brief = #{userBrief}")
    boolean updateUserInfo(User user);

}
