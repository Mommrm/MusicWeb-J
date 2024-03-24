package com.example.demo.mapper;

import com.example.demo.entity.PlayList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlayListMapper {
    @Insert("Insert into playlist(playlistId,songId,playlistName) values (#{playlistId},#{songId},#{playlistName})")
    public int insertSong(PlayList playList);

    //删除歌单中单个的歌曲
    @Delete("Delete From playlist where playlistId = #{playlistId} and songId = #{songId}")
    public boolean deleteSong_playlist(@Param("playlistId") String playlistId ,@Param("songId") String songId);

    @Insert("Insert into up(playlistId,playlistName,userId) values (#{playlistId},#{playlistName},#{userId})")
    public int createPlaylist(@Param("playlistId") String playlistId, @Param("playlistName") String playlistName ,@Param("userId") String userId);
    //删除用户创建的歌单
    @Delete("Delete From up where playlistId = #{playlistId} and userId = #{userId}")
    public int deleteUser_playlist(@Param("playlistId") String playlistId , @Param("userId") String userId);

}
