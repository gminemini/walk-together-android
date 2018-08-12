package com.custu.project.walktogether.controller.tmse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.custu.project.walktogether.util.DialogUtil;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionFourteenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener, MediaPlayer.OnCompletionListener {
    private ProgressDialog progressDialog;

    private boolean isPlaying;
    private MediaPlayer mediaPlayer;
    private String pathSound;
    private Button nextBtn;
    private RadioGroup radioGroup;

    private ImageView playSoundImageView;
    private NumberQuestion numberQuestion;
    private Question question;
    private String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_fourteen);
        getData();
        initValue();
        initProgress();
        setUI();
        setListener();
        countDownTime();
    }

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
        final int[] time = {31};
        final ProgressBar progress;
        progress = findViewById(R.id.progress);
        progress.setMax(time[0]);
        progress.setProgress(time[0]);
        countDownTimer = new CountDownTimer(timeInterval, 1000) {
            public void onTick(long millisUntilFinished) {
                progress.setProgress(--time[0]);
            }

            public void onFinish() {
                progress.setProgress(0);
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no14", question.getId(), "");
                Intent intent = new Intent(QuestionFourteenActivity.this, QuestionFifteenActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitEvaluation(this);
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(QuestionFourteenActivity.this);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void initValue() {
        isPlaying = false;
        mediaPlayer = new MediaPlayer();
        //pathSound = "http://159.65.128.189:8181/audio/question/recall/14/243/1519236211535.mp3";
        pathSound = ConfigService.BASE_URL_IMAGE + question.getAudio();
    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        playSoundImageView = findViewById(R.id.play_sound);
        radioGroup = findViewById(R.id.radio_group);
        TextView titleTextView = findViewById(R.id.question_text);
        titleTextView.setText("(14) " + question.getTitle());
        playSoundImageView.setOnClickListener(this);
        initAnswer();
    }

    private void initAnswer() {
        ArrayList<String> list = new ArrayList<>();
        list.add(numberQuestion.getAnswerArrayList().get(0).getAnswer());
        Collections.addAll(list, EvaluationModel.getInstance().getDummyChoiceRadioBox());
        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            RadioButton radioButton = new RadioButton(QuestionFourteenActivity.this);
            radioButton.setText(list.get(i));
            radioGroup.addView(radioButton, i);
        }
    }

    private String getAnswer() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked()) {
                answer = radioButton.getText().toString();
            }
        }
        return answer;
    }

    @Override
    public void getData() {
        numberQuestion = EvaluationModel.getInstance().getEvaluationByNumber("14", QuestionFourteenActivity.this);
        question = numberQuestion.getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    public void playSound() {
        String credential = Credentials.basic(ConfigService.USERNAME, ConfigService.PASSWORD);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Uri uri = Uri.parse(pathSound);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", credential);
        mediaPlayer = new MediaPlayer();
        Method method;
        try {
            method = mediaPlayer.getClass().getMethod("setDataSource", Context.class, Uri.class, Map.class);

            method.invoke(mediaPlayer, this, uri, headers);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();

            if (!isPlaying) {
                progressDialog.show();
                isPlaying = true;
                mediaPlayer.setOnPreparedListener(mp -> {
                    progressDialog.dismiss();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                    mp.start();
                });

                mediaPlayer.setOnCompletionListener(this);

            } else {
                stopPlaying();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
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
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no14", question.getId(), getAnswer());
                Intent intent = new Intent(QuestionFourteenActivity.this, QuestionFifteenActivity.class);
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
        countDownTimer.cancel();
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
