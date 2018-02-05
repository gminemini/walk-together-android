package com.custu.project.walktogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
    private ListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;
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
        getData();
    }

    @Override
    public void initValue() {

    }

    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public void setUI() {
        listView = findViewById(R.id.list_patient);
        PatientAdapter patientAdapter = new PatientAdapter(getApplicationContext(), patientArrayList);
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
