package com.custu.project.walktogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.PatientAdapter;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.ErrorDialog;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class HomeCaretakerActivity extends Activity implements BasicActivity {
    private SwipeMenuListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;
    private SwipeMenuCreator creator;

    private ArrayList<Patient> patientArrayList;


    OnDataSuccessListener patientListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                pullRefreshLayout.setRefreshing(false);
                patientArrayList = PatientModel.getInstance().getListPatients(object);
                Collections.reverse(patientArrayList);
                setUI();
                setListener();
            } else {
                pullRefreshLayout.setRefreshing(false);
                ErrorDialog.getInstance().showDialog(HomeCaretakerActivity.this, object.get("message").getAsString());
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecaretaker);
        initProgressDialog();
        SwipeMenuCreator();
        getData();
    }

    @Override
    public void initValue() {

    }

    private void SwipeMenuCreator() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setTitle("ลบ");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setBackground(R.color.colorDelete);
                deleteItem.setWidth((int) dp2px(80));
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private float dp2px(int dip) {
        float scale = getResources().getDisplayMetrics().density;
        return dip * scale + 0.5f;
    }

    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePatient(patientArrayList.get(index).getPatientNumber(), Long.parseLong("221"), index);
                        break;
                }
                return false;
            }
        });
    }

    private void deletePatient(String patientNumber, Long caretakerId, final int index) {
        ConnectServer
                .getInstance()
                .delete(new OnDataSuccessListener() {
                            @Override
                            public void onResponse(JsonObject object, Retrofit retrofit) {
                                int status = object.get("status").getAsInt();
                                if (status == 200) {
                                    patientArrayList.remove(index);
                                    setUI();
                                } else {
                                    ErrorDialog.getInstance().showDialog(HomeCaretakerActivity.this, object.get("message").getAsString());
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
                        }, ConfigService.MATCHING
                                + ConfigService.MATCHING_REMOVE_PATIENT
                                + caretakerId
                                + ConfigService.MATCHING_PATIENT_NUMBER
                                + patientNumber
                );

    }

    @Override
    public void setUI() {
        listView = findViewById(R.id.list_patient);
        PatientAdapter patientAdapter = new PatientAdapter(getApplicationContext(), patientArrayList);
        listView.setMenuCreator(creator);
        listView.setAdapter(patientAdapter);
    }

    @Override
    public void getData() {
        pullRefreshLayout = findViewById(R.id.refresh_layout);
        pullRefreshLayout.setRefreshing(true);
        ConnectServer.getInstance().get(patientListener, ConfigService.MATCHING +
                ConfigService.MATCHING_PATIENT_UNDER_CARETAKER + 221);
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(HomeCaretakerActivity.this);
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
}
