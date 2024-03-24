package com.example.demo.entity;

public class PlayList {
    private String playlistId;
    private String songId;
    private String playlistName;

    public PlayList(String playlistId, String songId, String playlistName){
        this.playlistId = playlistId;
        this.songId = songId;
        this.playlistName = playlistName;
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "playlistId='" + playlistId + '\'' +
                ", songId='" + songId + '\'' +
                ", playlistName='" + playlistName + '\'' +
                '}';
    }

    public String getPlaylistID() {
        return playlistId;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistId = playlistID;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
