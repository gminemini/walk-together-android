package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.MapMissionAdapter;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class SelectMissionFragment extends Fragment implements BasicActivity, View.OnClickListener {
    private ProgressDialog progressDialog;
    private FragmentActivity context;
    private View view;
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
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        ListView listView = view.findViewById(R.id.list_map);
        listView.setAdapter(new MapMissionAdapter(context, mapArrayList));

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
}
