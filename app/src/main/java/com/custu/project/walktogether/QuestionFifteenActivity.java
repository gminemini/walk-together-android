package com.custu.project.walktogether;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class QuestionFifteenActivity extends AppCompatActivity implements BasicActivity, View.OnTouchListener {
    private Question question;
    private ImageView imageView;
    private boolean goneFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_fifteen);
        getData();
        setUI();
        setListener();
        countDownTime();
    }

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
        final int[] time = {21};
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
                StoreAnswerTmse.getInstance().storeAnswer("no15", question.getId(), "");
                Intent intent = new Intent(QuestionFifteenActivity.this, QuestionSixteenActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
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
            goneFlag = true;
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
            case MotionEvent.ACTION_DOWN:
                if (goneFlag) {
                    goneFlag = false;
                    handler.postDelayed(mLongPressed, 2000);
                }
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(mLongPressed);
                return false;
            case MotionEvent.ACTION_MOVE:
                if (goneFlag) {
                    goneFlag = false;
                    handler.postDelayed(mLongPressed, 2000);
                }
                break;
        }
        return true;
    }
}
