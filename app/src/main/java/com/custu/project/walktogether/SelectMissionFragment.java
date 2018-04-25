package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.MapMissionAdapter;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class SelectMissionFragment extends Fragment implements BasicActivity, AdapterView.OnItemClickListener {
    private ProgressDialog progressDialog;
    private FragmentActivity context;
    private View view;
    private Long mapId;
    private ArrayList<Map> mapArrayList = new ArrayList<>();

    public SelectMissionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_choosemap, container, false);
        initProgressDialog();
        getData();
        return view;
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        ListView listView = view.findViewById(R.id.list_map);
        listView.setAdapter(new MapMissionAdapter(context, mapArrayList));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void getData() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    mapArrayList = MissionModel.getInstance().getMapArrayList(object);
                    setUI();
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
        }, ConfigService.MISSION + ConfigService.MAP_ALL);
        setUI();

    }

    private void getMission(Long idMap) {
        progressDialog.show();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object != null) {
                    UserManager.getInstance(context).storeMission(MissionModel.getInstance().getMissionArrayList(object));
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("mapId", mapId);
                    startActivity(intent);
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
        }, ConfigService.MISSION + idMap);
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(getString(R.string.loading));
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
    public void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mapId = mapArrayList.get(i).getId();
        getMission(mapArrayList.get(i).getId());
    }
}
