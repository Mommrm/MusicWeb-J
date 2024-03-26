package com.example.demo.controller;


import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.entity.Song;
import com.example.demo.service.Impl.ISongService;
import com.example.demo.service.SongService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("song")
public class SongController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String lyrics = "";
    @Resource
    private ISongService songService;

    @PostMapping("/getlyric")
    public Result getLyric(@RequestParam("songId") String songId) throws IOException {
        return songService.GetLyric(songId);
    }
    /**
     * @author Mommrm
     * @param songId
     * @return List<Comment>
     */

    @PostMapping("/getComments")
    public Result getComments(String songId){
        return songService.GetComments(songId);
    }

    @PostMapping("/updataCommentLikes")
    public Result updataCommentLikes(@RequestBody Comment comment){
        return songService.UpdataCommentLikes(comment);
    }

    @PostMapping("/search")
    //返回一组满足要求的Song对象
    public Result searchSong(@RequestBody Song song) throws Exception {
        return songService.SearchSong(song);
    }
}
