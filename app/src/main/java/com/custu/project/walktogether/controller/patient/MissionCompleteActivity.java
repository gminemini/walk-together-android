package com.custu.project.walktogether.controller.patient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MissionCompleteActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener  {
    private TextView timeTextView;
    private TextView distanceTextView;
    private TextView resultTextView;
    private Button button;
    private ImageView levelUp;

    private Long time;
    private int distance;
    private int resultScore;
    private int oldLevel;
    private Patient patient;
    private Animation levelUpAnimation;
    private Animation exitAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mission_complete);
        setUI();
        getData();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MissionCompleteActivity.this, ReceiveRewardActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initValue() {
        timeTextView.setText(String.valueOf(convertTime(time)));
        distanceTextView.setText(String.valueOf(distance) + " เมตร");
        resultTextView.setText(String.valueOf(resultScore + " คะแนน"));

        if (oldLevel != patient.getLevel()) {
            levelUp.setVisibility(View.VISIBLE);
            levelUp.startAnimation(levelUpAnimation);
            int splashInterval = 2000;
            new Handler().postDelayed(() -> {
                levelUp.startAnimation(exitAnimation);
                levelUp.setVisibility(View.GONE);
            }, splashInterval);
        }
    }

    @Override
    public void setUI() {
        levelUpAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        exitAnimation = AnimationUtils.loadAnimation(this, R.anim.exit_fragment);
        levelUp = findViewById(R.id.level_up);
        timeTextView = findViewById(R.id.time);
        distanceTextView = findViewById(R.id.distance);
        resultTextView = findViewById(R.id.resultScore);
        button = findViewById(R.id.next);
        button.setOnClickListener(this);
    }

    @Override
    public void getData() {
        oldLevel = UserManager.getInstance(MissionCompleteActivity.this).getPatient().getLevel();
        time = getIntent().getLongExtra("time", 0);
        distance = getIntent().getIntExtra("distance", 0);
        resultScore = getIntent().getIntExtra("resultScore", 0);
        patient = UserManager.getInstance(MissionCompleteActivity.this).getPatient();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    patient = PatientModel.getInstance().getPatient(object);
                    UserManager.getInstance(MissionCompleteActivity.this).storePatient(patient);
                    initValue();
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Throwable t) {
                NetworkUtil.isOnline(MissionCompleteActivity.this, timeTextView);
            }
        }, ConfigService.PATIENT + patient.getId());
        setUI();
    }

    @Override
    public void initProgressDialog() {

    }

    @SuppressLint("DefaultLocale")
    private String convertTime(Long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onBackPressed() {

    }
}
