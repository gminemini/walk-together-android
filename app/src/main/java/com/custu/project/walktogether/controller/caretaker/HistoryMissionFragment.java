package com.custu.project.walktogether.controller.caretaker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.ListHistoryMissionAdapter;
import com.custu.project.walktogether.controller.patient.HistoryMissionDetailActivity;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class HistoryMissionFragment extends Fragment implements BasicActivity, AdapterView.OnItemClickListener {
    private View view;
    private FragmentActivity context;
    private ListView listView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView noDataTextView;

    private Long patientId;
    private ArrayList<HistoryMission> historyMissionArrayList;

    public HistoryMissionFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_history_mission, container, false);
        setUI();
        getData();
        return view;
    }


    @Override
    public void initValue() {
        if (historyMissionArrayList.size() > 0) {
            listView.setAdapter(new ListHistoryMissionAdapter(context, historyMissionArrayList));
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
        view.findViewById(R.id.head).setVisibility(View.GONE);
        listView = view.findViewById(R.id.list);
        noDataTextView = view.findViewById(R.id.no_data);
        listView.setOnItemClickListener(this);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void getData() {
        if (getArguments() != null) {
            patientId = getArguments().getLong("idPatient");
            ConnectServer.getInstance().get(new OnDataSuccessListener() {
                @Override
                public void onResponse(JsonObject object, Retrofit retrofit) {
                    if (object != null) {
                        historyMissionArrayList = MissionModel.getInstance().getHistoryMissionArrayList(object);
                        Collections.reverse(historyMissionArrayList);
                        int splashInterval = new Random().nextInt(1500) + 500;
                        new Handler().postDelayed(() -> initValue(), splashInterval);
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
                    NetworkUtil.isOnline(context, listView);

                }
            }, ConfigService.MISSION + ConfigService.HISTORY_MISSION + patientId);
        }
    }

    @Override
    public void initProgressDialog() {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String data = new Gson().toJson(historyMissionArrayList.get(i).getPatientGame().getPatientMissionList());
        Intent intent = new Intent(context, HistoryMissionDetailActivity.class);
        intent.putExtra("mission", data);
        intent.putExtra("route", historyMissionArrayList.get(i).getPatientGame().getRoute());
        startActivity(intent);
    }
}
