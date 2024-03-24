package com.example.demo.mapper;


import com.example.demo.entity.PlayList;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select userId from user")
    public String[] findAllId();

    @Select("select userId from user where account = #{account} and password=#{password}")
    public String findLandId(String account ,String password);

    @Select("select userId , userName ,  account , password from user where userId = #{userId}")
    public User findUser(String userId);

    @Select("select userId,UserName,account,password from user where account = #{account} And password = #{password}")
    public User Land(User user);

    @Insert("insert into user(userId,userName,account,password) values (#{userId} , #{userName} , #{account} ,#{password}) ")
    public int Register(User user);

    @Select("select userName , account from user where userName = #{userName} And account = #{account}")
    public List<User> isExistEqualUser(String userName ,String account);

    @Select("select playlistId,playlistName,userId from up where userId = #{userId}")
    public ArrayList<PlayList> getMyPlaylist(String UserId);

    @Select("Select playlistName from up where playlistId = #{playlistId}")
    public String getPlaylistName(String playlistId);

}
