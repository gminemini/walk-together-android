package com.custu.project.walktogether.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.caretaker.ReHomeCaretakerActivity;
import com.custu.project.walktogether.controller.patient.ReHomePatientActivity;
import com.custu.project.walktogether.controller.tmse.ConditionActivity;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DeviceToken;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends Activity implements BasicActivity, View.OnClickListener {
    private Button registerBtn;
    private Button loginBtn;
    private Button forgetBtn;
    private EditText username;
    private EditText password;
    private LinearLayout tv;
    private ProgressDialog progressDialog;
    private CircularProgressButton circularProgressButton;

    private Caretaker caretaker;
    private Patient patient;

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
        forgetBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        circularProgressButton.setOnClickListener(this);
        findViewById(R.id.logo).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register: {
                Intent intent = new Intent(LoginActivity.this, ChooseuserActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.forgetpass: {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.login: {
                if (NetworkUtil.isOnline(LoginActivity.this, loginBtn))
                    if (validate())
                        login();
            }
            case R.id.sign_in: {
                if (validate()) {
                    circularProgressButton.startAnimation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            login();
                        }
                    }, 1500);
                }
            }

        }
    }

    private boolean validate() {

        if (username.length() == 0)
            username.setError("กรุณาใส่บัญชีผู้ใช้");

        if (password.length() == 0)
            password.setError("กรุณาใส่รหัสผ่าน");

        return username.length() != 0 && password.length() != 0;

    }

    public void login() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", username.getText().toString().trim());
        jsonObject.addProperty("password", password.getText().toString().trim());
        jsonObject.addProperty("deviceToken", DeviceToken.getInstance().getToken(LoginActivity.this));

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(final JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    int status = object.get("status").getAsInt();
                    if (status == 200) {
                        progressDialog.dismiss();
                        if (object.get("type").getAsString().equals("patient")) {
                            circularProgressButton.revertAnimation(new OnAnimationEndListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onAnimationEnd() {
                                    circularProgressButton.setText("เข้าสู่ระบบสำเร็จ");
                                    circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                    circularProgressButton.setBackgroundResource(R.drawable.shapebutton_complete);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            patient = PatientModel.getInstance().getPatient(object);
                                            UserManager.getInstance(LoginActivity.this).storePatient(patient);
                                            if (!object.get("isTestEvaluation").getAsBoolean()) {
                                                Intent intent = new Intent(LoginActivity.this, ReHomePatientActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, ConditionActivity.class);
                                                DialogUtil.getInstance().showDialogStartIntent(LoginActivity.this, getString(R.string.evaluation_dialog), intent);
                                            }
                                        }
                                    }, 700);
                                }
                            });
                        } else {
                            circularProgressButton.revertAnimation(new OnAnimationEndListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onAnimationEnd() {
                                    circularProgressButton.setText("เข้าสู่ระบบสำเร็จ");
                                    circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                    circularProgressButton.setBackgroundResource(R.drawable.shapebutton_complete);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            caretaker = CaretakerModel.getInstance().getCaretaker(object);
                                            UserManager.getInstance(LoginActivity.this).storeCaretaker(caretaker);
                                            Intent intent = new Intent(LoginActivity.this, ReHomeCaretakerActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 700);
                                }
                            });
                        }

                    } else {
                        circularProgressButton.revertAnimation(new OnAnimationEndListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onAnimationEnd() {
                                circularProgressButton.setText(object.get("message").getAsString());
                                circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                circularProgressButton.setBackgroundResource(R.drawable.shapebutton_error);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        circularProgressButton.startAnimation();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                circularProgressButton.revertAnimation();
                                                circularProgressButton.setText("เข้าสู่ระบบ");
                                                circularProgressButton.setTextColor(Color.parseColor("#389A1E"));
                                                circularProgressButton.setBackgroundResource(R.drawable.shapebutton_normal);
                                            }
                                        }, 1000);

                                    }
                                }, 2000);
                            }
                        });

                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {
                Snackbar.make(registerBtn, "onBodyError", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onBodyErrorIsNull() {
                Snackbar.make(registerBtn, "onBodyErrorIsNull", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                circularProgressButton.startAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        circularProgressButton.revertAnimation();
                        circularProgressButton.setText("เข้าสู่ระบบ");
                        circularProgressButton.setTextColor(Color.parseColor("#3F51B5"));
                        circularProgressButton.setBackgroundResource(R.drawable.shapebutton_normal);
                    }
                }, 1000);
                Snackbar.make(registerBtn, "กรุณาตรวจสอบการเชื่อมต่อเครือข่าย", Snackbar.LENGTH_LONG).show();
            }
        }, ConfigService.LOGIN, jsonObject);

    }

    @Override
    public void setUI() {

         tv = findViewById(R.id.nameapp);
        SpannableString content = new SpannableString("WALKTOGETHER");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        tv.setText(content);


        forgetBtn = findViewById(R.id.forgetpass);
        registerBtn = (Button) findViewById(R.id.register);
        loginBtn = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        circularProgressButton = (CircularProgressButton) findViewById(R.id.sign_in);
        circularProgressButton.setBackgroundResource(R.drawable.shapebutton);
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
        circularProgressButton.dispose();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}