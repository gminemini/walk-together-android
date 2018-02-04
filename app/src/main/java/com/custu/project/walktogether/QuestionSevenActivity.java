package com.custu.project.walktogether;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.network.callback.OnDownloadSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class QuestionSevenActivity extends Activity implements BasicActivity, View.OnClickListener {
    private boolean isPlaying;
    private MediaPlayer mp;
    private String pathSound;

    private ImageView playSoundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_question7);
        initValue();
        setUI();

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mp = new MediaPlayer();
        pathSound = "http://159.65.10.67:8181/audio/question/recall/14/5/1517736789671.mp3";
    }

    @Override
    public void setUI() {
        playSoundImageView = findViewById(R.id.play_sound);
        playSoundImageView.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    public void playSound() {

        if (!isPlaying) {
            isPlaying = true;
            MediaPlayer player = MediaPlayer.create(QuestionSevenActivity.this, Uri.parse(pathSound));
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } else {
            isPlaying = false;
            stopPlaying();
        }
    }

    private void stopPlaying() {
        mp.release();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.play_sound:
                playSound();
                break;
        }
    }
}
