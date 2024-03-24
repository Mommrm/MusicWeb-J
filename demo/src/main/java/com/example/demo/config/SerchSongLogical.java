package com.example.demo.config;

import com.example.demo.entity.Song;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SerchSongLogical {
    //获取歌曲
    public static List<Song> SearchMusic(String songName) throws Exception {

        ArrayList<Song> songsResult = new ArrayList<>();
        String songId;  //歌曲Id
        String songInfo;//歌曲信息 名字
        String songSinger;//歌手名字
        String songUrl;//歌曲下载url
        String encodeName = URLEncoder.encode(songName,"UTF-8");

        String url = "http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s="
                + encodeName + "&type=1&offset=0" //搜索歌曲名字
                + "&total=true&limit=20";
        URL Search = new URL(url);
        URLConnection conn = Search.openConnection();
        InputStream inputStream = conn.getInputStream();
        File songid = new File("songId.txt");
        FileOutputStream fs = new FileOutputStream(songid);
        byte[] bytes = new byte[1024];
        int byteRead;
        while ((byteRead = inputStream.read(bytes)) != -1){
            fs.write(bytes,0,byteRead);
        }
        fs.close();
        inputStream.close();
        //获取下载地址
        File txtFile = new File("songId.txt");
        FileInputStream iStream = new FileInputStream(txtFile);
        InputStreamReader reader = new InputStreamReader(iStream,"UTF-8");//创建一个输入流对象reader,并把提出来的字符串转换为UTF-8
        BufferedReader bfr = new BufferedReader(reader);
        //把txt文件中的全部字符串提取出来
        String json = bfr.readLine();
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

        bfr.close();
        reader.close();
        iStream.close();

        return songsResult;
    }
}
