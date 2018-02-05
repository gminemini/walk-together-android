package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ProgressDialogCustom;


public class QuestionSevenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private boolean isPlaying;
    private MediaPlayer mp;
    private String pathSound;
    private Button nextBtn;

    private ImageView playSoundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_question7);
        initValue();
        setUI();

        setUI();
        setListener();

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mp = new MediaPlayer();
        pathSound = "http://159.65.10.67:8181/audio/question/recall/14/5/1517736789671.mp3";
    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
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
            playSoundImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause));
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
            case R.id.next: {
                Intent intent = new Intent(QuestionSevenActivity.this, TopicsThreeActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ProgressDialogCustom.getInstance(QuestionSevenActivity.this) != null && ProgressDialogCustom.getInstance(QuestionSevenActivity.this).isShowing()) {
            ProgressDialogCustom.getInstance(QuestionSevenActivity.this).cancel();
        }
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
    }


}
