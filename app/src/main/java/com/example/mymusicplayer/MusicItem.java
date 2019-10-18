package com.example.mymusicplayer;

public class MusicItem {
    private String Title;
    private String Path;

    public MusicItem(String Title, String Path)
    {
        this.Path = Path;
        this.Title = Title;
    }

    public String getTitle(){
        return  this.Title;
    }

    public String getPath(){
        return this.Path;
    }
}
