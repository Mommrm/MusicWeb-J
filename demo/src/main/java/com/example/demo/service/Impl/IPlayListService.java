package com.example.demo.service.Impl;

import com.example.demo.entity.PlayList;
import com.example.demo.entity.Result;
import com.example.demo.entity.Song;
import com.example.demo.entity.songUser;
import com.example.demo.mapper.PlayListMapper;
import com.example.demo.mapper.SongMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IPlayListService implements PlayListService {
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result AddPlayList(String playlistId ,String playlistName, String userId  ) {
        try{//playlistId需要经过处理
            playlistId = getMyPlaylistId(playlistId);
            int insert_row = playListMapper.createPlaylist(playlistId,playlistName,userId);
            if(insert_row >= 1){
                return Result.ok(true);
            }
            else{
                return Result.fail("创建失败");
            }
        }catch (Exception e){
            System.out.println(e);
            return Result.fail("创建异常");
        }
    }

    /**
     *
     * Id是用户id＋当前日期Id
     * @param playlistId
     * @return
     */
    private String getMyPlaylistId(String playlistId){
        //获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        //转换为String类型
        String originalString = String.valueOf(currentDateTime);
        // 将原始字符串解析为LocalDateTime对象
        LocalDateTime dateTime = LocalDateTime.parse(originalString, DateTimeFormatter.ISO_DATE_TIME);
        // 使用自定义格式化模式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmm");
        // 格式化日期时间对象为处理后的字符串
        String processedString = dateTime.format(formatter);
        //拼接后返回
        return playlistId + processedString;
    }

    /**
     * 删除用户歌单
     * @param playlistId
     * @param userId
     * @return
     */
    @Override
    public Result DeletePlayList(String playlistId, String userId) {
        //删除默认歌单时候会返回-1错误
        if(userId.equals(playlistId)){
            return Result.fail("-1");
        }else{
            //先删除所删除歌单中的所有歌曲
            songMapper.deleteAllSong_playlist(playlistId);
            return Result.ok(playListMapper.deleteUser_playlist(playlistId , userId));
        }
    }

    /**
     * 获取用户歌单
     * @param userId
     * @return
     */
    @Override
    public Result GetMyPlayList(String userId) {
        ArrayList<PlayList> playLists = userMapper.getMyPlaylist(userId);
        if (!playLists.isEmpty()) {
            return Result.ok(playLists);
        }
        return Result.fail("歌单获取失败!");
    }

    /**
     * 添加歌曲到我的歌单
     * @param song
     * @return
     */
    @Override
    public Result AddSongToPlayList(songUser song) {
        System.out.println(song);
        try{
            String PlaylistName = userMapper.getPlaylistName(song.getPlaylistID()); //通过歌单Id查询歌单名字
            PlayList selectPlaylist = new PlayList(song.getPlaylistID(),song.getSongId(),PlaylistName);//被选择插入的歌单 ，临时变量
            Song songAdd = new Song(); //创建添加的歌曲变量

            songAdd.setSongName(song.getSongName());
            songAdd.setSongId(song.getSongId());
            songAdd.setSongUrl(song.getSongUrl());
            songAdd.setSongSinger(song.getSongSinger());
            songAdd.setPlaylistID(song.getPlaylistID());

            int check = addSong(songAdd,selectPlaylist);
            System.out.println("check: " + check);
            if(check > 0){
                return Result.ok(1);
            }else{
                return Result.ok(0);//未更新数据库
            }
        }catch (Exception e) {
            System.out.println("异常: " + e);
            return Result.fail("0");
        }
    }
    @Transactional
    private int addSong(Song songAdd , PlayList selectPlaylist){
        int addSong_check = songMapper.insert(songAdd);
        int addPlaylist_check = playListMapper.insertSong(selectPlaylist); //在歌单里面添加歌曲
        return addPlaylist_check + addSong_check;
    }

    @Override
    public Result DeleteSongFromPlayList(String songId, String playlistId) {
        if(playListMapper.deleteSong_playlist(playlistId,songId) && songMapper.deleteSong_song(songId,playlistId) ){
            return Result.ok();
        }
        else{
            return Result.fail("删除歌曲失败!");
        }
    }

    @Override
    public Result GetPlayListSongData(String playlistId) {
        List<Song> songList = songMapper.GetPlayListSongData(playlistId);
        if (!songList.isEmpty()) {
            return Result.ok(songList);
        }
        return Result.fail("无歌曲");
    }


}
