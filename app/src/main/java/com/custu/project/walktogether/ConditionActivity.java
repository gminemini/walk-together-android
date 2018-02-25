package com.custu.project.walktogether;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ConditionActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private CheckBox checkBox;
    private PullRefreshLayout pullRefreshLayout;
    private LinearLayout contentLinearLayout;
    private String checktype = "";
    private TextView patientBtn, careBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        contentLinearLayout = findViewById(R.id.content);
        pullRefreshLayout = findViewById(R.id.loading);
        pullRefreshLayout.setRefreshing(true);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                if (checkBox.isChecked()) {
                    if (NetworkUtil.isOnline(ConditionActivity.this, nextBtn)) {
                        Intent intent = new Intent(ConditionActivity.this, QuestionOneActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Snackbar.make(nextBtn, "กรุณายอมรับเงื่อนไข", Snackbar.LENGTH_LONG).show();
                }
            }

        }
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        pullRefreshLayout.setRefreshing(false);
        contentLinearLayout.setVisibility(View.VISIBLE);
        nextBtn = (Button) findViewById(R.id.next);
        checkBox = findViewById(R.id.check);
    }

    @Override
    public void getData() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    UserManager.getInstance(ConditionActivity.this).storeTMSE(EvaluationModel.getInstance().getTmse(object));
                    setUI();
                    setListener();
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
                pullRefreshLayout.setRefreshing(false);
                pullRefreshLayout.setVisibility(View.GONE);
                NetworkUtil.isOnline(ConditionActivity.this, nextBtn);
            }
        }, ConfigService.EVALUATION + ConfigService.EVALUATION_RANDOM);
    }

    @Override
    public void initProgressDialog() {

    }
}
