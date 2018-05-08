package com.custu.project.walktogether.controller.mission.missiontwo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.CalculateScoreMission;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.StoreMission;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MissionTypegroupActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener{

    private EditText inputMission;
    private ImageView imageQuestion;
    private Button nextBtn;
    private Intent intent;
    private CountDownTimer countDownTimer;
    private Mission mission;

    private final int[] time = {31};
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mission_typegroup);
        getData();
        setUI();
        setListener();
        initValue();
        countDownTime();

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
        PicassoUtil.getInstance().setImage(this, mission.getMissionDetail().getImage(), imageQuestion);
        textView.setText(mission.getMissionDetail().getQuestion());
    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputMission = findViewById(R.id.input_missionfive);
        imageQuestion = findViewById(R.id.image);
        textView = findViewById(R.id.title);
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void getData() {
        mission = new Gson().fromJson(getIntent().getStringExtra("mission"), Mission.class);
    }

    private void storeAnswerMission(Mission mission) {
        double score;
        boolean isCorrectMission = MissionModel.getInstance().isCorrectMission(mission.getMissionDetail().getAnswerMissions().get(0).getAnswer(), inputMission.getText().toString().trim());
        if (isCorrectMission)
            score = CalculateScoreMission.getInstance().getScore(time[0], mission.getMissionDetail().getScore(), 31);
        else
            score = 0;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idMission", mission.getMissionDetail().getId());
        jsonObject.addProperty("idPosition", mission.getPosition().getId());
        jsonObject.addProperty("score", score);
        StoreMission.getInstance().storeAnswer(jsonObject);
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitMission(MissionTypegroupActivity.this);
       super.onBackPressed();
    }
}
