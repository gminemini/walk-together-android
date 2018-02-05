package com.custu.project.walktogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.ErrorDialog;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class LoginActivity extends Activity implements BasicActivity ,View.OnClickListener{
    private Button registerBtn;
    private Button loginBtn;
    private EditText username;
    private EditText password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initProgressDialog();
        setUI();
        setListener();
    }

    @Override
    public void initValue() {

    }

    private void setListener() {
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register: {
                Intent intent = new Intent(LoginActivity.this, ChooseuserActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.login: {
                if (validate()) {
                   login();
                }
            }

        }
    }
    private boolean validate() {

        if (username.length() == 0)
            username.setError("กรุณาใส่บัญชีผู้ใช้");

        if (password.length() == 0)
            password.setError("กรุณาใส่รหัสผ่าน");

        return username.length() != 0 &&  password.length() != 0 ;

    }

    public void login() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", username.getText().toString().trim());
        jsonObject.addProperty("password", password.getText().toString().trim());

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object!=null) {
                    int status = object.get("status").getAsInt();
                    if (status == 200) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        ErrorDialog.getInstance().showDialog(LoginActivity.this, object.get("message").getAsString());
                    }
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
        }, ConfigService.LOGIN, jsonObject);

    }

    @Override
    public void setUI() {
        registerBtn = (Button)findViewById(R.id.register);
        loginBtn = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.input_username);
        password = (EditText)findViewById(R.id.input_password);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
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
