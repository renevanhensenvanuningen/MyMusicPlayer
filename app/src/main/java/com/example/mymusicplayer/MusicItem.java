package com.example.mymusicplayer;

import java.io.Serializable;
import java.util.HashMap;

public class MusicItem implements Serializable {
    private String Title;
    private String Path;
    private String Composer;

    public MusicItem(String Title, String Path)
    {
        this.Path = Path;
        this.Title = Title;
    }


    public MusicItem(HashMap<String, String> hashMap)
    {
        this.Path = hashMap.get("path");
        this.Title = hashMap.get("title");
        this.Composer = hashMap.get("composer");
    }

    public String getTitle(){
        return  this.Title;
    }

    public String getPath(){
        return this.Path;
    }
}
