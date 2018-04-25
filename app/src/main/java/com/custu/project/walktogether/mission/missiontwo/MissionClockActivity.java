package com.custu.project.walktogether.mission.missiontwo;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.StoreMission;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MissionClockActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener{
    private Spinner answerSpinner_hour;
    private Spinner answerSpinner_minus;
    private ArrayList<String> answerArray_hour = new ArrayList<String>();
    private ArrayList<String> answerArray_minus = new ArrayList<String>();

    private EditText inputMision;
    private ImageView imageQuestion;
    private Button nextBtn;
    private Intent intent;
    private CountDownTimer countDownTimer;
    private Mission mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mission_clock);
        getData();
        setUI();
        setListener();
        countDownTime();
        createSpinnerData();
        ArrayAdapter<String> adapterArray_hour = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray_hour);
        ArrayAdapter<String> adapterArray_minus = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray_minus);
        answerSpinner_hour.setAdapter(adapterArray_hour);
        answerSpinner_minus.setAdapter(adapterArray_minus);

    }
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
//                StoreAnswerTmse.getInstance().storeAnswer("no5", question.getId(), "");
//                Intent intent = new Intent(QuestionFiveActivity.this, QuestionSixActivity.class);
//                startActivity(intent);
            }
        }.start();
    }

//    private void showDialog(Context context) {
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.dialog);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        TextView titleTextView = dialog.findViewById(R.id.title);
//        titleTextView.setText(inputMision.getText() + " " + titleTextView.getText());
//
//        LinearLayout done = dialog.findViewById(R.id.submit);
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                countDownTimer.cancel();
//                intent = new Intent(QuestionTwelveActivity.this, QuestionThirteenActivity.class);
//                StoreAnswerTmse.getInstance().storeAnswer("no12", question.getId(), inputTopicFive.getText().toString());
//                dialog.dismiss();
//                startActivity(intent);
//            }
//        });
//        LinearLayout cancel = dialog.findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                inputTopicFive.setText("");
//            }
//        });
//        dialog.show();
//    }

    @Override
    public void onClick(View v) {
        storeAnswerMission(mission);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("index", getIntent().getIntExtra("index", 0));
        returnIntent.putExtra("isComplete", true);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        answerSpinner_hour = (Spinner) findViewById(R.id.input_hour);
        answerSpinner_minus = (Spinner) findViewById(R.id.input_minus);

        nextBtn = (Button) findViewById(R.id.next);
        inputMision = findViewById(R.id.input_missionfive);
        imageQuestion = findViewById(R.id.image);
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void initProgressDialog() {

    }
    private void createSpinnerData() {
        answerArray_hour.add("เลือกคำตอบ");
        answerArray_minus.add("เลือกคำตอบ");
        answerArray_hour.add("0");
        answerArray_hour.add("1");
        answerArray_hour.add("2");
        answerArray_hour.add("3");
        answerArray_hour.add("4");
        answerArray_hour.add("5");
        answerArray_hour.add("6");
        answerArray_hour.add("7");
        answerArray_hour.add("8");
        answerArray_hour.add("9");
        answerArray_hour.add("10");
        answerArray_hour.add("11");
        answerArray_hour.add("12");
        answerArray_hour.add("13");
        answerArray_hour.add("14");
        answerArray_hour.add("15");
        answerArray_hour.add("16");
        answerArray_hour.add("17");
        answerArray_hour.add("18");
        answerArray_hour.add("19");
        answerArray_hour.add("20");
        answerArray_hour.add("21");
        answerArray_hour.add("22");
        answerArray_hour.add("23");
        answerArray_hour.add("24");

        for (int i = 0 ;i<60;i++){
            answerArray_minus.add(""+i+"");
        }

    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitMission(MissionClockActivity.this);
        super.onBackPressed();
    }

    @Override
    public void getData() {
        mission = new Gson().fromJson(getIntent().getStringExtra("mission"), Mission.class);
    }

    private void storeAnswerMission(Mission mission) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idMission", mission.getMissionDetail().getId());
        jsonObject.addProperty("idPosition", mission.getPosition().getId());
        jsonObject.addProperty("score", mission.getMissionDetail().getScore());
        StoreMission.getInstance().storeAnswer(jsonObject);
    }
}
