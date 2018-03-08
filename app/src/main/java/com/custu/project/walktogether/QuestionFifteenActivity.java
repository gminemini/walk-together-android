package com.custu.project.walktogether;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
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
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        TextView titleTextView = findViewById(R.id.question_text);
        titleTextView.setText("(15) "+question.getTitle());
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
