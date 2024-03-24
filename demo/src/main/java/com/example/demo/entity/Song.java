package com.example.demo.entity;

public class Song {
    private String songName;
    private String songSinger;

    private String songUrl;

    private String songId;

    private String playlistId;

    public String getPlaylistID() {
        return playlistId;
    }

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

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", songSinger='" + songSinger + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", songId='" + songId + '\'' +
                ", playlistId=" + playlistId +
                '}';
    }
}
