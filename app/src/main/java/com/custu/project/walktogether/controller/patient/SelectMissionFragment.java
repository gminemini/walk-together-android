package com.custu.project.walktogether.controller.patient;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.MapMissionAdapter;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.UserManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class SelectMissionFragment extends Fragment implements BasicActivity, AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks {
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    private ProgressDialog progressDialog;
    private FragmentActivity context;
    private View view;
    private Long mapId;
    private ArrayList<Map> mapArrayList = new ArrayList<>();
    private PullRefreshLayout pullRefreshLayout;

    private GoogleApiClient mGoogleApiClient;
    private double currentLatitude;
    private double currentLongitude;
    private ListView listView;

    public SelectMissionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_choosemap, container, false);
        initProgressDialog();
        setUI();
        requestPermission();
        initLocation();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    context, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        }
    }

    private void initLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void initValue() {
        pullRefreshLayout.setRefreshing(false);
        listView.setAdapter(new MapMissionAdapter(context, mapArrayList));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void setUI() {
        listView = view.findViewById(R.id.list_map);
        pullRefreshLayout = view.findViewById(R.id.refresh_layout);
        pullRefreshLayout.setOnRefreshListener(this::initLocation);
    }

    @Override
    public void getData() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    mapArrayList = MissionModel.getInstance().getMapArrayList(object);
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
        }, ConfigService.MISSION + ConfigService.MAP_ALL + "nearness?lat=" + currentLatitude + "&lng=" + currentLongitude);
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        @Nullable Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
        } else {
            currentLatitude = 0;
            currentLongitude = 0;
        }
        getData();
    }

    @Override
    public void onConnectionSuspended(int i) {
        currentLatitude = 0;
        currentLongitude = 0;
        getData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                } else {
                    // permission was denied
                }
                return;
            }
        }
    }
}
