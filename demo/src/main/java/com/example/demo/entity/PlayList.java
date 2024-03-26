package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@TableName("music_playlist_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {
    private String playlistId;
    private String playlistName;
    private long playTime;
    private String playlistAvatar;
    private String playlistBrief;
    private Date playlistCreteTime;
    private long playlistCollectionNum;

}
