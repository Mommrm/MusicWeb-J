package com.example.demo.service;


import com.example.demo.entity.Result;
import com.example.demo.entity.songUser;
import org.springframework.stereotype.Service;

@Service
public interface PlayListService {
    //创建歌单
    public Result AddPlayList(String playlistId ,String playlistName, String userId);
    //删除歌单
    public Result DeletePlayList(String playlistId,String userId);
    //获取歌单
    public Result GetMyPlayList(String userId);
    //添加歌曲到歌单
    public Result AddSongToPlayList(songUser song);
    //从歌单删除歌曲
    public Result DeleteSongFromPlayList(String songId, String playlistId);

    public Result GetPlayListSongData(String playlistId);

}
