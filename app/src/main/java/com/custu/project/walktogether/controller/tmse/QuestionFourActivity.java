package com.custu.project.walktogether.controller.tmse;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.custu.project.walktogether.util.DialogUtil;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionFourActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private Button nextBtn;
    private TextView titleTextView;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_four);
        getData();
        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
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
                StoreAnswerTmse.getInstance().storeAnswer("no4", question.getId(), "");
                Intent intent = new Intent(QuestionFourActivity.this, QuestionFiveActivity.class);
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

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
        nextBtn = (Button) findViewById(R.id.next);
        titleTextView = findViewById(R.id.title);
        titleTextView.setText("(4) " + question.getTitle());
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("4", QuestionFourActivity.this).getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    private void createSpinnerData() {
        answerArray.add("เช้ามืด");
        answerArray.add("เช้า ");
        answerArray.add("สาย");
        answerArray.add("เที่ยง");
        answerArray.add("บ่าย");
        answerArray.add("เย็น");
        answerArray.add("หัวค่ำ");
        answerArray.add("กลางคืน");
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no4", question.getId(), answerSpinner.getSelectedItem().toString());
                Intent intent = new Intent(QuestionFourActivity.this, QuestionFiveActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
