package com.custu.project.walktogether.controller.tmse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionEighteenActivity extends AppCompatActivity implements BasicActivity {
    private Button nextBtn;
    private EditText inputEditText;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_eighteen);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getData();
        setUI();
        nextBtn.setOnClickListener(v -> showDialog(QuestionEighteenActivity.this));
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
                StoreAnswerTmse.getInstance().storeAnswer("no18", question.getId(), "");
                Intent intent = new Intent(QuestionEighteenActivity.this, QuestionNineteenActivity.class);
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
        titleTextView.setText("' "+inputEditText.getText().toString()+" '");

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(view -> {
            countDownTimer.cancel();
            StoreAnswerTmse.getInstance().storeAnswer("no18", question.getId(), inputEditText.getText().toString());
            Intent intent = new Intent(QuestionEighteenActivity.this, QuestionNineteenActivity.class);
            dialog.dismiss();
            startActivity(intent);
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
            inputEditText.setText("");
        });
        dialog.show();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputEditText = (EditText) findViewById(R.id.input_topicfive);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText(question.getTitle() + "เหมือนกันคือ");
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("18", QuestionEighteenActivity.this)
                .getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
