package com.example.demo.mapper;

import cn.hutool.core.date.DateTime;
import com.example.demo.entity.PlayList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlayListMapper {
    @Insert("Insert into music_playlist_info(music_playlist_id,songId,playlistName) values (#{playlistId},#{songId},#{playlistName})")
    public int insertSong(PlayList playList);

    //删除歌单中单个的歌曲
    @Delete("Delete From playlist where playlistId = #{playlistId} and songId = #{songId}")
    public boolean deleteSong_playlist(@Param("playlistId") String playlistId ,@Param("songId") String songId);

    @Insert("Insert into music_playlist_info(music_playlist_id,music_playlist_name,music_playlist_cretime) values (#{playlistId},#{playlistName},#{now})")
    public int createPlaylist(String playlistId, String playlistName , DateTime now);
    @Insert("Insert into music_user_playlist(music_user_id,music_playlist_id,music_playlist_name) values (#{userId},#{playlistId},#{playlistName})")
    public int createUPlaylist(String userId, String playlistId , String playlistName);
    //删除用户创建的歌单
    @Delete("Delete From up where playlistId = #{playlistId} and userId = #{userId}")
    public int deleteUser_playlist(@Param("playlistId") String playlistId , @Param("userId") String userId);

}
