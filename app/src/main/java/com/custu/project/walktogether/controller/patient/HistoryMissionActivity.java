package com.custu.project.walktogether.controller.patient;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.ListHistoryMissionAdapter;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class HistoryMissionActivity extends AppCompatActivity implements BasicActivity, AdapterView.OnItemClickListener {
    private ListView listView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView noDataTextView;

    private Patient patient;
    private ArrayList<HistoryMission> historyMissionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_history_mission);
        setUI();
        getData();
    }

    @Override
    public void initValue() {
        if (historyMissionArrayList.size() > 0) {
            listView.setAdapter(new ListHistoryMissionAdapter(HistoryMissionActivity.this, historyMissionArrayList));
            listView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.GONE);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
        }
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void setUI() {
        noDataTextView = findViewById(R.id.no_data);
        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
    }

    @Override
    public void getData() {
        patient = UserManager.getInstance(HistoryMissionActivity.this).getPatient();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object!=null) {
                    historyMissionArrayList = MissionModel.getInstance().getHistoryMissionArrayList(object);
                    int splashInterval = new Random().nextInt(1500)+500;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initValue();
                        }
                    }, splashInterval);
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
                NetworkUtil.isOnline(HistoryMissionActivity.this, listView);

            }
        }, ConfigService.MISSION + ConfigService.HISTORY_MISSION + patient.getId());
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String data = new Gson().toJson(historyMissionArrayList.get(i).getPatientGame().getPatientMissionList());
        Intent intent = new Intent(HistoryMissionActivity.this, HistoryMissionDetailActivity.class);
        intent.putExtra("mission", data);
        intent.putExtra("route", historyMissionArrayList.get(i).getPatientGame().getRoute());
        startActivity(intent);
    }
}
