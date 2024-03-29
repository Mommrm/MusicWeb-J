package com.example.demo.service.Impl;

import com.example.demo.config.SerchSongLogical;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.entity.Song;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.SongService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
@Service
public class ISongService implements SongService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String lyrics;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result GetLyric(String songId) {

        String url = "http://music.163.com/api/song/media?id=" + songId;

        try{
            URL apiUrl = new URL(url);
            // 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            // 设置请求方法
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // 解析JSON数据
                String json = response.toString();
                JsonNode jsonNode = objectMapper.readTree(json);
                lyrics = jsonNode.get("lyric").asText();
                return Result.ok(lyrics);
            } else {
                System.out.println("请求失败，错误代码：" + responseCode);
                return Result.ok("无歌词，请欣赏");
            }
        }catch (Exception e){
            System.out.println("捕获异常" + e);
            return Result.fail("歌曲获取失败");
        }
    }

    @Override
    public Result GetComments(String songId) {
        try{
            List<Comment> comments = commentMapper.getSongComment(songId);
            return Result.ok(comments);
        }catch (Exception e){
            return Result.fail("获取评论失败");
        }
    }

    @Override
    public Result UpdataCommentLikes(Comment comment) {
        try{
            int i = commentMapper.updateLikes(comment);
            if (i>0) {
                Result.ok();
            }
            return Result.fail("无法更新不存在的评论点赞数");
        }catch (Exception e){
            System.out.println(e);
            return Result.fail("更新点赞数失败");
        }
    }

    @Override
    public Result SearchSong(String songName) throws Exception {
        List<Song> searchResult;

        searchResult = SerchSongLogical.SearchMusic(songName);

        if (!searchResult.isEmpty()) {
            return Result.ok(searchResult);
        }
        return Result.fail("搜索失败");
    }
}
