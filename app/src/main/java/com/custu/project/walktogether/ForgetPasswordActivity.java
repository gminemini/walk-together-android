package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.DialogQrCode;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ForgetPasswordActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private TextView username;
    private Button submitBtn;
    private String password;
    private String tell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_password);
        setUI();
        setListener();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit: {
                sendUsername();

                break;
            }


        }
    }

    private void sendUsername() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("contact", username.getText().toString().trim());

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object.get("status").getAsInt() == 200) {
                    JsonObject data = object.getAsJsonObject("data");
                    password = data.get("password").getAsString();
                    tell = data.get("tell").getAsString();
                    sendSMS();
                } else if (object.get("status").getAsInt() == 404) {
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
                NetworkUtil.isOnline(ForgetPasswordActivity.this, username);

            }
        }, ConfigService.FORGET_PASSWORD, jsonObject);
    }

    private void sendSMS() {
        ConnectServer.getInstance().sendSMS(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {

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
        }, ConfigService.SMS_API + ConfigService.SMS_API_BASE + tell + ConfigService.SMS_MESSAGE + "รหัสผ่านของคุณคือ " + password);

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

    }
}
