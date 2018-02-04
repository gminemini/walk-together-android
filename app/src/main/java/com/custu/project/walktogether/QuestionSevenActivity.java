package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.io.IOException;

public class QuestionSevenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private boolean isPlaying;
    private MediaPlayer mp;
    private String pathSound;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_question7);

        setUI();
        setListener();

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mp = new MediaPlayer();
    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    public void playSonud() {

        if (!isPlaying) {
            isPlaying = true;
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(pathSound);
                mp.prepare();
                mp.start();
            } catch (IOException e) {

            }
        } else {
            isPlaying = false;
            stopPlaying();
        }
    }

    private void stopPlaying() {
        mp.release();
        mp = null;
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                Intent intent = new Intent(QuestionSevenActivity.this, TopicsThreeActivity.class);
                startActivity(intent);
            }

        }
    }
}
