package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.ProgressDialogCustom;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionNineteenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private Question question;
    private Question questionRef;
    private NumberQuestion numberQuestion;
    private RadioGroup radioGroup;
    private Long id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_nineteen);
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
                StoreAnswerTmse.getInstance().storeAnswerNineteen("no19", question.getId(), questionRef.getId(), "");
                sendEvaluation();
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
        nextBtn = (Button) findViewById(R.id.next);
        radioGroup = findViewById(R.id.radio_group);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText("(19) " + question.getTitle());
        initProgress();
        initAnswer();
    }

    @Override
    public void getData() {
        numberQuestion = EvaluationModel.getInstance()
                .getEvaluationByNumber("7", QuestionNineteenActivity.this);
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("19", QuestionNineteenActivity.this)
                .getQuestion();
        questionRef = EvaluationModel.getInstance()
                .getEvaluationByNumber("7", QuestionNineteenActivity.this)
                .getQuestion();
    }

    private void initAnswer() {
        String answer = numberQuestion.getAnswerArrayList().get(0).getAnswer();
        answer += EvaluationModel.getInstance().getDummyChoiceCheckBox();
        String[] answerArray = answer.split(",");
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, answerArray);
        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            CheckBox checkBox = new CheckBox(QuestionNineteenActivity.this);
            checkBox.setText(list.get(i));
            radioGroup.addView(checkBox, i);
        }
    }

    private String getAnswer() {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) radioGroup.getChildAt(i);
            if (checkBox.isChecked()) {
                list.add(checkBox.getText().toString());
            }
        }
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i != list.size() - 1)
                builder.append(",");
        }
        return builder.toString();
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        countDownTimer.cancel();
        switch (v.getId()) {
            case R.id.next: {
                if (NetworkUtil.isOnline(QuestionNineteenActivity.this, radioGroup)) {
                    StoreAnswerTmse.getInstance().storeAnswerNineteen("no19", question.getId(), questionRef.getId(), getAnswer());
                    sendEvaluation();
                }
            }
        }
    }

    private void sendEvaluation() {
        JsonObject jsonObject = StoreAnswerTmse.getInstance().getAllAnswer();
        progressDialog.show();
        if (UserManager.getInstance(QuestionNineteenActivity.this).getPatient() != null) {
            id = UserManager.getInstance(QuestionNineteenActivity.this).getPatient().getId();
        } else {
            id = 0L;
        }
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    int score = object.getAsJsonObject("data").get("score").getAsInt();
                    boolean isPass = object.getAsJsonObject("data").get("isPass").getAsBoolean();
                    if (isPass) {
                        Intent intent = new Intent(QuestionNineteenActivity.this, ResultPassActivity.class);
                        intent.putExtra("idPatient", object.getAsJsonObject("data").get("idPatient").getAsLong());
                        intent.putExtra("score", score);
                        storePatient(intent, object.getAsJsonObject("data").get("idPatient").getAsLong());
                    } else {
                        Intent intent = new Intent(QuestionNineteenActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {
                progressDialog.dismiss();
            }

            @Override
            public void onBodyErrorIsNull() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                NetworkUtil.isOnline(QuestionNineteenActivity.this, radioGroup);
            }
        }, ConfigService.EVALUATION + ConfigService.EVALUATION_CHECK + id, jsonObject);
    }

    private void storePatient(final Intent intent, Long id) {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object != null) {
                    UserManager.getInstance(QuestionNineteenActivity.this).storePatient(PatientModel.getInstance().getPatient(object));
                    startActivity(intent);
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {
                progressDialog.dismiss();
            }

            @Override
            public void onBodyErrorIsNull() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                NetworkUtil.isOnline(QuestionNineteenActivity.this, radioGroup);
            }
        }, ConfigService.PATIENT + id);


    }

    private void initProgress() {
        progressDialog = new ProgressDialog(QuestionNineteenActivity.this);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}