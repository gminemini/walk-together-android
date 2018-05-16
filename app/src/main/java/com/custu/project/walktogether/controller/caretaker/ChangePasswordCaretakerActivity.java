package com.custu.project.walktogether.controller.caretaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private RelativeLayout saveImageView;
    private ProgressDialog progressDialog;

    private Caretaker caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_password_caretaker);
        initProgressDialog();
        getData();
        setUI();
    }

    @Override
    public void initValue() {
    }

    @Override
    public void setUI() {
        oldPasswordEditText = findViewById(R.id.old_password);
        newPasswordEditText = findViewById(R.id.new_password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        saveImageView = findViewById(R.id.save);
        saveImageView.setOnClickListener(this);
    }

    @Override
    public void getData() {
        caretaker = UserManager.getInstance(ChangePasswordCaretakerActivity.this).getCaretaker();
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(ChangePasswordCaretakerActivity.this);
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

    public void changePassword() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", confirmPasswordEditText.getText().toString().trim());
        ConnectServer.getInstance().update(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object != null) {
                    if (object.get("status").getAsInt() == 201) {
                        caretaker = CaretakerModel.getInstance().getCaretaker(object);
                        UserManager.getInstance(ChangePasswordCaretakerActivity.this).storeCaretaker(caretaker);
                        startActivity(new Intent(ChangePasswordCaretakerActivity.this, ReHomeCaretakerActivity.class));
                    } else {
                        NetworkUtil.showMessageResponse(ChangePasswordCaretakerActivity.this,
                                oldPasswordEditText,
                                object.get("message").getAsString());

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
                progressDialog.dismiss();
                NetworkUtil.isOnline(ChangePasswordCaretakerActivity.this, oldPasswordEditText);
            }
        }, ConfigService.CARETAKER + caretaker.getId(), jsonObject);
    }

    @Override
    public void onClick(View view) {
        if (validate())
            changePassword();

    }

    private boolean validate() {
        boolean validate = true;

        if (oldPasswordEditText.length() == 0) {
            oldPasswordEditText.setError("กรุณาใส่รหัสผ่าน");
            oldPasswordEditText.requestFocus();
            validate = false;
        } else if (!(oldPasswordEditText.getText().toString().equals(caretaker.getPassword()))) {
            oldPasswordEditText.setError("รหัสผ่านเดิมไม่ถูกต้อง");
            oldPasswordEditText.requestFocus();
            validate = false;
        }

        if (newPasswordEditText.length() == 0) {
            newPasswordEditText.setError("กรุณาใส่รหัสผ่านใหม่");
            newPasswordEditText.requestFocus();
            validate = false;
        }

        if (confirmPasswordEditText.length() == 0) {
            confirmPasswordEditText.setError("กรุณาใส่ยืนยันรหัสผ่าน");
            confirmPasswordEditText.requestFocus();
            validate = false;
        } else if (!(confirmPasswordEditText.getText().toString().equals(newPasswordEditText.getText().toString()))) {
            confirmPasswordEditText.setError("กรุณาใส่รหัสผ่านให้ตรงกัน");
            confirmPasswordEditText.requestFocus();
            validate = false;
        }

        return validate;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
