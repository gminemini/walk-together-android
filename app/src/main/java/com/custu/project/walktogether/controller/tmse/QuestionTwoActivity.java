package com.custu.project.walktogether.controller.tmse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.custu.project.walktogether.util.DialogUtil;

import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionTwoActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private TextView titleTextView;
    private EditText edittextBtn;
    private NumberQuestion numberQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_two);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
                StoreAnswerTmse.getInstance().storeAnswer("no2", numberQuestion.getQuestion().getId(), "0");
                Intent intent = new Intent(QuestionTwoActivity.this, QuestionThreeActivity.class);
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

    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);


        titleTextView.setText("' "+ edittextBtn.getText() + " " + titleTextView.getText()+"'");


        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(view -> {
            countDownTimer.cancel();
            StoreAnswerTmse.getInstance().storeAnswer("no2", numberQuestion.getQuestion().getId(), edittextBtn.getText().toString());
            Intent intent = new Intent(QuestionTwoActivity.this, QuestionThreeActivity.class);
            dialog.dismiss();
            startActivity(intent);
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
            edittextBtn.setText("");
        });
        dialog.show();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        edittextBtn = (EditText) findViewById(R.id.input_two);
        titleTextView = findViewById(R.id.title);
        titleTextView.setText("(2) " + numberQuestion.getQuestion().getTitle());
    }

    @Override
    public void getData() {
        numberQuestion = EvaluationModel.getInstance().getEvaluationByNumber("2", QuestionTwoActivity.this);
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                showDialog(QuestionTwoActivity.this);
            }

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
