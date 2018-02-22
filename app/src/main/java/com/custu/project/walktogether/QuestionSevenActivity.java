package com.custu.project.walktogether;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
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


public class QuestionSevenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener, MediaPlayer.OnCompletionListener {
    private ProgressDialog progressDialog;

    private boolean isPlaying;
    private MediaPlayer mediaPlayer;
    private String pathSound;
    private Button nextBtn;

    private ImageView playSoundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_seven);
        initValue();
        initProgress();
        setUI();
        setUI();
        setListener();
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(QuestionSevenActivity.this);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mediaPlayer = new MediaPlayer();
        pathSound = "http://159.65.128.189:8181/audio/question/recall/14/243/1519236211535.mp3";
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
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (!isPlaying) {
            progressDialog.show();
            isPlaying = true;
            mediaPlayer = MediaPlayer.create(QuestionSevenActivity.this, Uri.parse(pathSound));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressDialog.dismiss();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                    mp.start();
                }
            });

            mediaPlayer.setOnCompletionListener(this);

        } else {
            stopPlaying();
        }
    }

    private void stopPlaying() {
        playSoundImageView.setImageDrawable(getResources().getDrawable(R.drawable.speaker));
        isPlaying = false;
        progressDialog.dismiss();
        mediaPlayer.release();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.play_sound:
                playSoundImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                playSound();
                break;
            case R.id.next: {
                Intent intent = new Intent(QuestionSevenActivity.this, QuestionEightActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playSoundImageView.setImageDrawable(getResources().getDrawable(R.drawable.speaker));
        isPlaying = false;
        stopPlaying();
    }
}
