package com.example.demo.config;

import com.example.demo.DTO.Songs;
import com.example.demo.entity.Song;
import com.example.demo.service.SongService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SerchSongLogical {
    @Resource
    private static RestTemplate restTemplate;


    //获取歌曲
    public static List<Song> SearchMusic(String songName) throws Exception {
        System.out.println("--------------搜索请求----------------");
        ArrayList<Song> songsResult = new ArrayList<>();
        Songs songs = new Songs();
        String songId;  //歌曲Id
        String songInfo;//歌曲信息 名字
        String songSinger;//歌手名字
        String songUrl;//歌曲下载url

        String url = "http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s="
                + songName + "&type=1&offset=0" //搜索歌曲名字
                + "&total=true&limit=20";
        // 获取搜索内容的Json格式的String
        String json = getInstance("UTF-8").getForObject(url,String.class);
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int i = 0;
            // 解析JSON
            JsonNode songsArrayNode = objectMapper.readTree(json).get("result").get("songs");
            // 遍历歌曲数组
            for (JsonNode songNode : songsArrayNode) {
                /*重点List add()方法是记录地址所以要每次都创建一个新的对象，最后才不会出现重复*/
                Song tempSong = new Song(); //创建临时歌曲实体变量
                // 获取相应的值
                songId = String.valueOf(songNode.get("id").asInt());
                songInfo = songNode.get("name").asText();

                JsonNode artistsArrayNode = songNode.get("artists");
                songSinger = artistsArrayNode.get(0).get("name").asText();
                songUrl = "http://music.163.com/song/media/outer/url?id=" + songId + ".mp3";
                //将临时歌曲实体变量赋值
                tempSong.setSongName(songInfo);
                tempSong.setSongSinger(songSinger);
                tempSong.setSongUrl(songUrl);
                tempSong.setSongId(songId);
                songsResult.add(i,tempSong);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return songsResult;
    }

    /**
     * 创建指定字符集的RestTemplate
     *
     * @param charset 编码
     * @return 返回结果
     */
    public static RestTemplate getInstance(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
        return restTemplate;
    }
}
