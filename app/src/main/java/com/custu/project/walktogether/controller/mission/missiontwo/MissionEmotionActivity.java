package com.custu.project.walktogether.controller.mission.missiontwo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.ChoiceAnswerMissionAdapter;
import com.custu.project.walktogether.data.mission.AnswerMission;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.CalculateScoreMission;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.StoreMission;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.ButtonClickAlpha;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MissionEmotionActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private EditText inputMision;
    private ImageView imageQuestion;
    private TextView titleTextView;
    private Button nextBtn;
    private Intent intent;
    private CountDownTimer countDownTimer;
    private Mission mission;

    private ArrayList<AnswerMission> answerMissions;
    private ChoiceAnswerMissionAdapter listAdapter;
    private int index = -1;
    private final int[] time = {31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mission_emotion);
        getData();
        setUI();
        setListener();
        countDownTime();

    }

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
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
        if (index != -1) {
            storeAnswerMission(mission);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("index", getIntent().getIntExtra("index", 0));
            returnIntent.putExtra("isComplete", true);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            NetworkUtil.showMessageResponse(this, listView, "กรุณาเลือกคำตอบ");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initValue() {
        TextView levelTextView = findViewById(R.id.show_level);
        levelTextView.setText("Lv. "+ UserManager.getInstance(this).getPatient().getLevel());
        titleTextView.setText(DataFormat.getInstance().addDoubleCode(mission.getMissionDetail().getQuestion()));
        listAdapter = new ChoiceAnswerMissionAdapter(this, answerMissions);
        listView.setAdapter(listAdapter);
        PicassoUtil.getInstance().setImage(MissionEmotionActivity.this, mission.getMissionDetail().getImage(), imageQuestion);
    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputMision = findViewById(R.id.input_missionfive);
        imageQuestion = findViewById(R.id.image);
        listView = findViewById(R.id.list);
        titleTextView = findViewById(R.id.title);
        ButtonClickAlpha.getInstance().setAlphaAnimation(nextBtn);
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void getData() {
        mission = new Gson().fromJson(getIntent().getStringExtra("mission"), Mission.class);
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    answerMissions = MissionModel.getInstance().getAnswerMissions(object);
                    answerMissions.add(mission.getMissionDetail().getAnswerMissions().get(0));
                    Collections.shuffle(answerMissions);
                    initValue();
                }

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, ConfigService.MISSION + ConfigService.DUMMY_CHOICE + mission.getMissionDetail().getId());
    }

    private void storeAnswerMission(Mission mission) {
        double score;
        boolean isCorrectMission = MissionModel.getInstance().isCorrectMission(mission.getMissionDetail().getAnswerMissions().get(0).getAnswer(), answerMissions.get(index).getAnswer());
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
        DialogUtil.getInstance().showDialogExitMission(MissionEmotionActivity.this, mission.getMissionDetail().getId(), mission.getPosition().getId());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        this.index = index;
        for (int i = 0; i < adapterView.getCount(); i++) {
            View v = adapterView.getChildAt(i);
            LinearLayout linearLayout = v.findViewById(R.id.parent);
            TextView textView = v.findViewById(R.id.choice);
            linearLayout.setBackground(getResources().getDrawable(R.drawable.shape_answer));
            textView.setTextColor(Color.parseColor("#3FB53F"));
            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        LinearLayout linearLayout = view.findViewById(R.id.parent);
        TextView textView = view.findViewById(R.id.choice);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.shape_answer_non_select));
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setBackgroundColor(Color.parseColor("#3FB53F"));
    }
}
