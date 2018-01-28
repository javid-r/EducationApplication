package com.exercises.javid.educationapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private Activity context;
    private HashMap<String, String> record;
    private MediaPlayer player;
    private Button btnPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnPlayPause = findViewById(R.id.btn_play);
        context = this;
        record = null;
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String id = bundle.getString(DatabaseHandler.T_ID);
            if (!(Objects.equals(id, null) && Objects.equals(id, ""))) {
                record = new DatabaseHandler(context).getDataRecord(id);
                if (record != null) {
                    loadContent();
                }
            }
        }


    }

    private void loadContent() {

        player = new MediaPlayer();

        String title = record.get(DatabaseHandler.T_NAME);
        String desc = record.get(DatabaseHandler.T_DESC);

        int image = context.getResources().getIdentifier(
                record.get(DatabaseHandler.T_IMG),
                "drawable",
                context.getPackageName());

        final int voice = context.getResources().getIdentifier(
                record.get(DatabaseHandler.T_VO),
                "raw",
                context.getPackageName());

        ((TextView) findViewById(R.id.txt_title)).setText(title);
        ((TextView) findViewById(R.id.txt_desc)).setText(desc);
        ((ImageView) findViewById(R.id.img_content)).setImageResource(image);
        player = MediaPlayer.create(context, voice);


        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) {
                        pausePlayer();
                    } else {
                        startPlayer();
                    }
                }
            }
        });

    }

    private void startPlayer() {
        if (player != null) {
            player.start();
            btnPlayPause.setBackgroundResource(R.drawable.ic_pause);
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.pause();
            btnPlayPause.setBackgroundResource(R.drawable.ic_play);
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
