package com.custu.project.walktogether.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPasswordActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private TextView username;
    private Button submitBtn;
    private String password;
    private String tell;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_password);
        initProgressDialog();
        setUI();
        setListener();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit: {
                if (username.getText().length() != 0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View view = this.getCurrentFocus();
                    if (imm != null) {
                        if (view != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                    sendUsername();
                    break;
                } else {
                    username.setError("กรุณาใส่เบอร์โทรศัพท์");
                    username.requestFocus();
                }
            }
        }
    }

    private void sendUsername() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("contact", username.getText().toString().trim());

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object.get("status").getAsInt() == 200) {
                    JsonObject data = object.getAsJsonObject("data");
                    password = data.get("password").getAsString();
                    tell = data.get("tell").getAsString();
                    sendSMS();
                } else if (object.get("status").getAsInt() == 404) {
                    username.setText("");
                    NetworkUtil.showMessageResponse(ForgetPasswordActivity.this, username, object.get("message").getAsString());
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
                progressDialog.dismiss();
                NetworkUtil.isOnline(ForgetPasswordActivity.this, username);
            }
        }, ConfigService.FORGET_PASSWORD, jsonObject);
    }

    private void sendSMS() {
        String url = ConfigService.SMS_API + tell + ConfigService.SMS_MESSAGE + "WalkTogether: รหัสผ่านใหม่ของคุณคือ " + password;
        ConnectServer.getInstance().sendSMS(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object.get("status").getAsInt() == 200) {
                    NetworkUtil.showMessageResponse(ForgetPasswordActivity.this, username, "รหัสผ่านใหม่ส่งไปยังเบอร์โทรศัพท์ของท่าน");
                    int splashInterval = 3000;
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }, splashInterval);
                } else {
                    username.setText("");
                    NetworkUtil.showMessageResponse(ForgetPasswordActivity.this, username, "เกิดข้อผิดพลาด");
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
                progressDialog.dismiss();
                NetworkUtil.isOnline(ForgetPasswordActivity.this, username);
            }
        }, url);

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        username = findViewById(R.id.number);
        submitBtn = findViewById(R.id.submit);

    }

    private void setListener() {
        username.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }


    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
