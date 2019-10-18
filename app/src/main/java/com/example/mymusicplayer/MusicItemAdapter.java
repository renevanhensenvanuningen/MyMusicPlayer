package com.example.mymusicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MusicItemAdapter extends ArrayAdapter<MusicItem>  {

    private Context mContext;

    private int pos ;

    private MediaPlayer player;

    private List<MusicItem> musicItems = new ArrayList();


    public MusicItemAdapter(@NonNull Context context,  ArrayList<MusicItem> musicItemsList) {
        super(context, 0 , musicItemsList);
        mContext = context;
        musicItems = musicItemsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View listItemview = convertView;
        if (listItemview == null){
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        pos = position;
        MusicItem currentMusicItem = musicItems.get(position);
        TextView titleTextView = (TextView) listItemview.findViewById(R.id.edtTitle);
        titleTextView.setText(currentMusicItem.getTitle());
        TextView pathTextView = (TextView) listItemview.findViewById(R.id.edtPath);
        titleTextView.setText(currentMusicItem.getTitle());

        Button btnStart = (Button) listItemview.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayFile(position);
            }
        });

        Button btnStop = (Button) listItemview.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopPlay();
            }
        });

        return listItemview;
    }

    private void PlayFile(int position)
    {
        Uri playUri = Uri.parse(musicItems.get(position).getPath());
        player = MediaPlayer.create(mContext, playUri );
        player.start();
 /*      try
       {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(mContext, playUri);
            player.prepare();
           player.start();
        }
        catch (IOException ex){}
*/
    }

    private void StopPlay()
    {
        if (player.isPlaying())
        player.stop();
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


}
