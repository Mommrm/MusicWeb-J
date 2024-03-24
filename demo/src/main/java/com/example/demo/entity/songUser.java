package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class songUser {
    private String songName;
    private String songSinger;

    private String songUrl;

    private String songId;

    private String playlistId;

    private String userId;
    private String userName;

    public String getPlaylistID() {
        return playlistId;
    }

    @JsonProperty(value = "playlistId")
    public void setPlaylistID(String playlistID) {
        this.playlistId = playlistID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongSinger() {
        return songSinger;
    }

    public void setSongSinger(String songSinger) {
        this.songSinger = songSinger;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
    @JsonProperty(value = "userId")
    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        this.userId = userID;
    }

    public String getUsername() {
        return userName;
    }
    @JsonProperty(value = "userName")
    public void setUsername(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "songUser{" +
                "songName='" + songName + '\'' +
                ", songSinger='" + songSinger + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", songId='" + songId + '\'' +
                ", playlistId=" + playlistId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
