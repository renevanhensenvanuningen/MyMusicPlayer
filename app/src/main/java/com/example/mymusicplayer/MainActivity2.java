package com.example.mymusicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends Activity {

    Context context;

    public static final int RUNTIME_PERMISSION_CODE = 7;

    MusicItem[] ListElements = new MusicItem[] { };

    ListView listView;

    ArrayList<MusicItem> ListElementsArrayList ;

    ArrayAdapter<MusicItem> adapter ;

    ContentResolver contentResolver;

    Cursor cursor;

    Uri uri;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView1);

        button = (Button) findViewById(R.id.button);

        context = getApplicationContext();
        ListElementsArrayList = new ArrayList<MusicItem>();
        adapter = new MusicItemAdapter(this, ListElementsArrayList);


        // Requesting run time permission for Read External Storage.


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


                GetAllMediaMp3Files(uri);
                Integer count = adapter.getCount();

                //adapter.insert("Sdcard count "+ count.toString(), 0);

                uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
                String playUriStr = GetAllMediaMp3Files(uri);
                Uri playUri = Uri.parse(playUriStr);
                Integer count2 = adapter.getCount() - 1 - count;
                // adapter.insert("Internal count "+ count2.toString(), 0);
                listView.setAdapter(adapter);


            }
        });
    }

    public void DoIets() {


        GetAllMediaMp3Files(uri);
        Integer count = adapter.getCount();

        //adapter.insert("Sdcard count "+ count.toString(), 0);

        uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        String playUriStr = GetAllMediaMp3Files(uri);
        Uri playUri = Uri.parse(playUriStr);
        Integer count2 = adapter.getCount() - 1 - count;
        // adapter.insert("Internal count "+ count2.toString(), 0);
        listView.setAdapter(adapter);
    }


    public String GetAllMediaMp3Files(Uri uri){

        contentResolver = context.getContentResolver();
        String filePath = "";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.COMPOSER,
                MediaStore.Audio.Media.DATA,    // filepath of the audio file
                MediaStore.Audio.Media._ID,     // context id/ uri id of the file
        };

        cursor = contentResolver.query(
                uri, // Uri
                projection,
                null,
                null,
                null
        );

        if (cursor == null) {

            Toast.makeText(MainActivity2.this,"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {

            Toast.makeText(MainActivity2.this,"No Music Found on SD Card.", Toast.LENGTH_LONG);

        }
        else {

            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int filePathIdx = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int composerIdx = cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
            //cursor.
            //Getting Song ID From Cursor.
            //int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do {

                // You can also get the Song ID using cursor.getLong(id).
                //long SongID = cursor.getLong(id);
                filePath = cursor.getString(filePathIdx);
                String SongTitle = cursor.getString(Title);
                // Adding Media File Names to ListElementsArrayList.
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("path", cursor.getString(filePathIdx));
                hm.put("title", cursor.getString(Title));
                hm.put("composer", cursor.getString(composerIdx));

                ListElementsArrayList.add(new MusicItem(hm));

            } while (cursor.moveToNext());
        }
        return filePath;
    }
}
