package com.custu.project.walktogether;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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
    private int PERMISSIONS_REQUEST_RECEIVE_SMS = 130;

    @SuppressLint("WrongConstant")
    private void sendSMS() {

        int hasSendSMSPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasSendSMSPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
            if (hasSendSMSPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                        PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

        SmsManager sms = SmsManager.getDefault();

        StringBuilder builder = new StringBuilder(tell);
        builder.setCharAt(0, '6');


//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "6" + builder));
//        intent.putExtra("sms_body", "รหัสผ่านของคุณคือ1 " + password);
//        startActivity(intent);

        sms.sendTextMessage("6" + builder, null, "รหัสผ่านของคุณคือ " + password, null, null);
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
