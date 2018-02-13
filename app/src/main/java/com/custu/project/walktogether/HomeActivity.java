package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.ErrorDialog;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class HomeActivity extends Activity implements BasicActivity, View.OnClickListener {
    private TextView nameTextView;
    private TextView levelTextView;
    private CircleImageView circleImageView;
    private PullRefreshLayout refreshLayout;

    private Patient patient;

    OnDataSuccessListener patientListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                patient = PatientModel.getInstance().getPatient(object);
                setUI();
            } else {
                ErrorDialog.getInstance().showDialog(HomeActivity.this, object.get("message").getAsString());
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
        setContentView(R.layout.activity_home);
        initValue();
        getData();
    }

    @Override
    public void initValue() {
        patient = UserManager.getInstance(HomeActivity.this).getPatient();
    }

    @Override
    public void setUI() {
        nameTextView = findViewById(R.id.name);
        levelTextView = findViewById(R.id.level);
        circleImageView = findViewById(R.id.image);
        setDataToUi();
    }

    @Override
    public void getData() {
        ConnectServer.getInstance().get(patientListener, ConfigService.PATIENT + patient.getId());
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void initProgressDialog() {
        circleImageView.setOnClickListener(this);

    }

    public void setListener() {
        circleImageView.setOnClickListener(this);
    }

    private void setDataToUi() {
        refreshLayout.setRefreshing(false);
        nameTextView = findViewById(R.id.name);
        circleImageView = findViewById(R.id.image);
        nameTextView.setText("คุณ" + patient.getFirstName() + " " + patient.getLastName());
        PicassoUtil.getInstance().setImageProfile(HomeActivity.this, patient.getImage(), circleImageView);
        setListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.image:{
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
