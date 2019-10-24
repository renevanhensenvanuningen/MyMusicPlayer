package com.example.mymusicplayer;


import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

// Adapter Class
public class MyFilterAdapter extends BaseAdapter implements Filterable {

    private ArrayList<MusicItem> mOriginalValues; // Original Values
    private ArrayList<MusicItem> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;
    Context context;
    private ViewGroup viewGroup;
    MediaPlayer player = new MediaPlayer();
    private View lastSelectedItem;
    private int playPosition;

    public MyFilterAdapter(Context context, ArrayList<MusicItem> mProductArrayList) {
        this.mOriginalValues = mProductArrayList;
        this.mDisplayedValues = mProductArrayList;
        inflater = LayoutInflater.from(context);
        this.context = context;

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                doOnComplete();
            }
        });
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        LinearLayout llContainer;
        TextView tvName,tvPrice;
    }

    private void doOnComplete(){
        if (playPosition <= mDisplayedValues.size() -1)
        {
           PlayFile(playPosition +1);
        }
        else PlayFile(0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listItemview = convertView;
        if (listItemview == null){
            listItemview = inflater.inflate(R.layout.list_item, parent, false);
        }

        MusicItem currentMusicItem = mDisplayedValues.get(position);
        TextView titleTextView = (TextView) listItemview.findViewById(R.id.edtTitle);
        titleTextView.setText(currentMusicItem.getTitle());
        TextView pathTextView = (TextView) listItemview.findViewById(R.id.edtPath);
        titleTextView.setText(currentMusicItem.getTitle());

        TextView tvAlbumComposer = (TextView) listItemview.findViewById(R.id.edtComposerArtist);
        tvAlbumComposer.setText(currentMusicItem.getArtistAndComposer());


        viewGroup = parent;

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayFileAndSetSelected(position, v);
            }
        });

        return listItemview;

    }

    public void PlayFile(int position)
    {
        playPosition = position;
        Uri playUri = Uri.parse(mDisplayedValues.get(position).getPath());
        if (player.isPlaying()) player.stop();
        player = MediaPlayer.create(context, playUri );
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                doOnComplete();
            }
        });
        player.start();
    }

    public void PausePlay()
    {
        if (player.isPlaying())
            player.pause();

    }

    public void StopPlay()
    {
        if (player.isPlaying())
            player.pause();

    }


    private  void PlayFileAndSetSelected(int position, View view){
        toggleBackgroundItem(view);
        PlayFile(position);
    }

    private void toggleBackgroundItem(View view) {
            if (lastSelectedItem != null) {
                lastSelectedItem.setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundColor(Color.BLUE);
            lastSelectedItem = view;

        }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<MusicItem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<MusicItem> FilteredArrList = new ArrayList<MusicItem>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<MusicItem>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getTitle();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("path", mOriginalValues.get(i).getPath());
                            hm.put("title", mOriginalValues.get(i).getTitle());
                            hm.put("composer", mOriginalValues.get(i).getComposer());
                            hm.put("artist", mOriginalValues.get(i).getArtist());
                            hm.put("album", mOriginalValues.get(i).getAlbum());
                            FilteredArrList.add(new MusicItem(hm));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}


