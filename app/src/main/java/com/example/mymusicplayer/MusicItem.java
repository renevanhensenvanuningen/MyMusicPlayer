package com.example.mymusicplayer;

import java.io.Serializable;
import java.util.HashMap;

public class MusicItem implements Serializable {
    private String Title;
    private String Path;
    private String Composer;
    private String Artist;

    public String getComposer() {
        return Composer;
    }

    public String getArtist() {
        return Artist;
    }

    public String getAlbum() {
        return Album;
    }

    private String Album;

    public MusicItem(String Title, String Path)
    {
        this.Path = Path;
        this.Title = Title;
    }

    public String getArtistAndComposer(){
        return Artist + " " + Composer;
    }


    public MusicItem(HashMap<String, String> hashMap)
    {
        this.Path = hashMap.get("path");
        this.Title = hashMap.get("title");
        this.Composer = hashMap.get("composer");
        this.Artist = hashMap.get("artist");
        this.Album = hashMap.get("album");

    }

    public String getTitle(){
        return  this.Title;
    }

    public String getPath(){
        return this.Path;
    }
}
