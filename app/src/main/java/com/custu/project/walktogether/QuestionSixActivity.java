package com.custu.project.walktogether;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class QuestionSixActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Intent intent;
    private Button nextBtn;
    private EditText edittextBtn;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_six);
        getData();
        setUI();
        setListener();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(QuestionSixActivity.this);

            }
        });

        countDownTime();
    }

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = 21000;
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
                StoreAnswerTmse.getInstance().storeAnswer("no6", question.getId(), "");
                intent = new Intent(QuestionSixActivity.this, QuestionSevenActivity.class);
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

    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);
        titleTextView.setText(edittextBtn.getText().toString());

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no6", question.getId(), edittextBtn.getText().toString());
                intent = new Intent(QuestionSixActivity.this, QuestionSevenActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextBtn.setText("");
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    @Override
    public void initValue() {

    }

    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        edittextBtn = (EditText) findViewById(R.id.input_six);
        ImageView imageView = findViewById(R.id.image);
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText("(6) "+question.getTitle());
        PicassoUtil.getInstance().setImage(QuestionSixActivity.this, question.getImage(), imageView);
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("6", QuestionSixActivity.this)
                .getQuestion();
    }

    @Override
    public void initProgressDialog() {


    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
