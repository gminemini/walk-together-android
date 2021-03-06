package com.custu.project.walktogether.controller.tmse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.custu.project.walktogether.util.DialogUtil;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionFifteenActivity extends AppCompatActivity implements BasicActivity, View.OnTouchListener {
    private Question question;
    private ImageView imageView;
    private boolean      goneFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_fifteen);
        getData();
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
                StoreAnswerTmse.getInstance().storeAnswer("no15", question.getId(), "0");
                Intent intent = new Intent(QuestionFifteenActivity.this, QuestionSixteenActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitEvaluation(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        TextView titleTextView = findViewById(R.id.question_text);
        titleTextView.setText("(15) " + question.getTitle());
        imageView = findViewById(R.id.touch);
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("15", QuestionFifteenActivity.this).getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        imageView.setOnTouchListener(this);
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            handler.removeCallbacks(mLongPressed);
            goneFlag = false;
            countDownTimer.cancel();
            StoreAnswerTmse.getInstance().storeAnswer("no15", question.getId(), String.valueOf(true));
            Intent intent = new Intent(QuestionFifteenActivity.this, QuestionSixteenActivity.class);
            startActivity(intent);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(mLongPressed);
                goneFlag = true;
                return false;
            case MotionEvent.ACTION_MOVE:
                if (goneFlag) {
                    handler.postDelayed(mLongPressed, 1700);
                }
                break;
        }
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
