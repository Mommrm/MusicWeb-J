package com.example.demo.mapper;


import com.example.demo.entity.Song;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SongMapper {

    @Insert("Insert into song(songId,songName,songSinger,songUrl,playlistId) values ( #{songId},#{songName},#{songSinger},#{songUrl},#{playlistId})")
    public int insert(Song song);
    //默认歌单
    @Select("select * from song where playlistId = #{playlistId}")
    public List<Song> GetPlayListSongData(String playlistId);

    //删除单个歌曲
    @Delete("Delete From song where songId = #{songId} and playlistId = #{playlistId}")
    public boolean deleteSong_song(String songId,String playlistId);

    //删除歌单中的所有歌曲
    @Delete("Delete From song where playlistId = #{playlistId}")
    public boolean deleteAllSong_playlist(@Param("playlistId") String playlistId);
}
