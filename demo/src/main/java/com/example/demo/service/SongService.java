package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.entity.Song;
import org.springframework.stereotype.Service;


public interface SongService {

    public Result GetLyric(String songId);

    public Result GetComments(String songId);

    public Result UpdataCommentLikes(Comment comment);

    public Result SearchSong(String songName) throws Exception;
}
