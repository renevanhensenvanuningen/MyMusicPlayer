package com.example.mymusicplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class MusicItemAdapter extends ArrayAdapter<MusicItem> {

    private Context mContext;

    private List<MusicItem> musicItems = new ArrayList();


    public MusicItemAdapter(@NonNull Context context,  ArrayList<MusicItem> musicItemsList) {
        super(context, 0 , musicItemsList);
        mContext = context;
        musicItems = musicItemsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemview = convertView;
        if (listItemview == null){
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        MusicItem currentMusicItem = musicItems.get(position);
        TextView titleTextView = (TextView) listItemview.findViewById(R.id.edtTitle);
        titleTextView.setText(currentMusicItem.getTitle());
        TextView pathTextView = (TextView) listItemview.findViewById(R.id.edtPath);
        titleTextView.setText(currentMusicItem.getTitle());

        return listItemview;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }
}
