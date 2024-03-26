package com.example.demo.controller;


import com.example.demo.entity.Result;
import com.example.demo.entity.songUser;
import com.example.demo.service.Impl.IPlayListService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("playlist")
public class PlaylistController {
    @Resource
    private IPlayListService playListService;

    @PostMapping("/addPlaylist")
    public Result createMyPlaylist(@RequestParam("playlistId") String playlistId , @RequestParam("playlistName") String playlistName
            , @RequestParam("userId") String userId ){
        return playListService.AddUserPlayList(playlistId,playlistName,userId);
    }

    @PostMapping("/deletePlaylist")
    public Result deletePlaylist(@RequestParam("playlistId") String playlistId , @RequestParam("userId") String userId){
        return playListService.DeletePlayList(playlistId, userId);
    }

    /**
     * 获取我的歌单列表
     * @param userId
     * @return
     */
    @PostMapping("/getMyPlaylist")
    public Result getMyPlaylist(@RequestParam("userId") String userId){
        return playListService.GetMyPlayList(userId);
    }
    @PostMapping("/insertMusic")
    public Result addSongToPlayList(@RequestBody songUser song) throws Exception{
        return playListService.AddSongToPlayList(song);
    }

    @PostMapping("/deleteMyMusic")
    public Result deleteMyMusic(@RequestParam("songId") String songId, @RequestParam("playlistId") String playlistId ){
        return playListService.DeleteSongFromPlayList(songId,playlistId);
    }

    @PostMapping("/getMySongData")
    public Result GetPlayListSongData(@RequestParam("playlistId") String playlistId) throws Exception{
        return playListService.GetPlayListSongData(playlistId);
    }
}
