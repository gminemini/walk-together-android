package com.custu.project.walktogether;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.io.IOException;

public class QuestionSevenActivity extends Activity implements BasicActivity {
    private boolean isPlaying;
    private MediaPlayer mp;
    private String pathSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_question7);

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mp = new MediaPlayer();
    }

    @Override
    public void setUI() {

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
}
