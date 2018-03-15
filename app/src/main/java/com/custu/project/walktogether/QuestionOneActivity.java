package com.custu.project.walktogether;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class QuestionOneActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Spinner answerSpinner;
    private TextView titleTextView;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private Button nextBtn;

    private NumberQuestion numberQuestion;
    private JsonObject answerTmse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_one);
        getData();
        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
        countDownTime();
    }

    @Override
    public void initValue() {

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
                StoreAnswerTmse.getInstance().storeAnswer("no1", numberQuestion.getQuestion().getId(), "");
                Intent intent = new Intent(QuestionOneActivity.this, QuestionTwoActivity.class);
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

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
        nextBtn = (Button) findViewById(R.id.next);
        titleTextView = findViewById(R.id.title);

        titleTextView.setText("(1) " + numberQuestion.getQuestion().getTitle());
    }

    @Override
    public void getData() {
        answerTmse = new JsonObject();
        numberQuestion = EvaluationModel.getInstance().getEvaluationByNumber("1", QuestionOneActivity.this);
    }

    @Override
    public void initProgressDialog() {

    }

    private void createSpinnerData() {
        answerArray.add("วันอาทิตย์");
        answerArray.add("วันจันทร์");
        answerArray.add("วันอังคาร");
        answerArray.add("วันพุธ");
        answerArray.add("วันพฤหัสบดี");
        answerArray.add("วันศุกร์");
        answerArray.add("วันเสาร์");
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no1", numberQuestion.getQuestion().getId(), answerSpinner.getSelectedItem().toString());
                Intent intent = new Intent(QuestionOneActivity.this, QuestionTwoActivity.class);
                startActivity(intent);
            }

        }
    }
}
