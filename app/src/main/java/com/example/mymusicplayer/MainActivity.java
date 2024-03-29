package com.example.mymusicplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        button = (Button)findViewById(R.id.button);

        context = getApplicationContext();
        ListElementsArrayList = new ArrayList<MusicItem>();

        SeekBar seekbar  = (SeekBar) findViewById(R.id.seekbar_audio);
        adapter = new MusicItemAdapter(this, ListElementsArrayList, seekbar );



        // Requesting run time permission for Read External Storage.
        AndroidRuntimePermission();

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
                Integer count2 = adapter.getCount() -1 - count ;
               // adapter.insert("Internal count "+ count2.toString(), 0);
                listView.setAdapter(adapter);


            }
        });

        Button btnIntent = (Button)findViewById(R.id.btnStartIntent);

        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartNewIntent();
            }
        });

        // ListView on item selected listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // Showing ListView Item Click Value using Toast.
                Toast.makeText(MainActivity.this,parent.getAdapter().getItem(position).toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    private  void StartNewIntent()
    {
        Intent myIntent = new Intent(this, MainActivity2.class);
        myIntent.putExtra("title", "Hoi"); //Optional parameters
        this.startActivity(myIntent);
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

            Toast.makeText(MainActivity.this,"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {

            Toast.makeText(MainActivity.this,"No Music Found on SD Card.", Toast.LENGTH_LONG);

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


    // Creating Runtime permission function.
    public void AndroidRuntimePermission(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
                    alert_builder.setMessage("External Storage Permission is Required.");
                    alert_builder.setTitle("Please Grant Permission.");
                    alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    RUNTIME_PERMISSION_CODE

                            );
                        }
                    });

                    alert_builder.setNeutralButton("Cancel",null);

                    AlertDialog dialog = alert_builder.create();

                    dialog.show();

                }
                else {

                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            RUNTIME_PERMISSION_CODE
                    );
                }
            }else {

            }
        }
    }
}
