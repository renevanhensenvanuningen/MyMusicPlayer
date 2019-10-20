package com.example.mymusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlayOneActivity extends Activity implements View.OnClickListener {
    private  MusicItem musicItem;
    private TextView tvTitle;
    private MediaPlayer player = new MediaPlayer();
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_playone);

       Intent intent = getIntent();
       String title = intent.getStringExtra("title");
       musicItem = (MusicItem) intent.getSerializableExtra("musicItem");
       tvTitle = (TextView) findViewById(R.id.textView);
       Play();

    }

    private void Play(){

        tvTitle.setText(musicItem.getTitle());
        Uri ur = Uri.parse(musicItem.getPath());
        player = new MediaPlayer();
        player = MediaPlayer.create(this.getApplicationContext(), ur);
        player.start();

    }

    @Override
    public void onClick(View v) {

        if (player.isPlaying()){
            player.stop();
        }

    }
}
