package com.custu.project.walktogether.controller.caretaker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.master.Education;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.MasterModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.custu.project.walktogether.util.DeviceToken;
import com.custu.project.walktogether.util.InitSpinnerDob;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.SwipeBack;
import com.google.gson.JsonObject;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputConfirmPass;
    private EditText inputFirstname;
    private EditText inputLastname;
    private EditText inputDob;
    private Spinner inputSex;
    private Spinner inputEducation;
    private Spinner inputDay;
    private Spinner inputMonth;
    private Spinner inputYear;
    private EditText inputTell;
    private EditText inputOccupation;
    private EditText inputEmail;
    private ProgressDialog progressDialog;
    private CircularProgressButton circularProgressButton;

    private ArrayList<Sex> sexArrayList = new ArrayList<>();
    private ArrayList<Education> educationArrayList = new ArrayList<>();

    private Long idSex;
    private Long idEducation;

    OnDataSuccessListener sexListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            if (object != null) {
                sexArrayList = MasterModel.getInstance().getSex(object);
                setSexSpinner();
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

    OnDataSuccessListener educationListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            if (object != null) {
                educationArrayList = MasterModel.getInstance().getEducations(object);
                setEducationSpinner();
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
        setContentView(R.layout.register_caretaker);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initProgressDialog();
        getData();
        setUI();
        setListener();
        Slidr.attach(this, SwipeBack.getInstance().confrig());
    }

    private void setDobSpinner() {
        ArrayAdapter<String> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerDay());
        inputDay.setAdapter(adapterArray);

        adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerMonth());
        inputMonth.setAdapter(adapterArray);

        adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerYear());
        inputYear.setAdapter(adapterArray);

        inputDay.setSelected(false);
        inputDay.setSelection(0, true);

        inputMonth.setSelected(false);
        inputMonth.setSelection(0, true);

        inputYear.setSelected(false);
        inputYear.setSelection(0, true);

    }

    private void setSexSpinner() {
        ArrayAdapter<Sex> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexArrayList);
        inputSex.setAdapter(adapterArray);
    }

    private void setEducationSpinner() {
        ArrayAdapter<Education> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, educationArrayList);
        inputEducation.setAdapter(adapterArray);
    }


    @Override
    public void setUI() {
        nextBtn = findViewById(R.id.next);
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPass = findViewById(R.id.input_confirm_pass);
        inputFirstname = findViewById(R.id.input_firstname);
        inputLastname = findViewById(R.id.input_lastname);
        inputDob = findViewById(R.id.input_dob);
        inputSex = findViewById(R.id.input_sex);
        inputEducation = findViewById(R.id.input_education);

        inputTell = findViewById(R.id.input_tell);
        inputOccupation = findViewById(R.id.input_occupation);
        inputEmail = findViewById(R.id.input_email);

        inputDay = findViewById(R.id.day);
        inputMonth = findViewById(R.id.month);
        inputYear = findViewById(R.id.year);

        circularProgressButton = findViewById(R.id.register_button);
        circularProgressButton.setBackgroundResource(R.drawable.shapetopics);
        setDobSpinner();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button: {
                if (NetworkUtil.isOnline(RegisterCaretakerActivity.this, circularProgressButton))
                    if (validate()) {
                        circularProgressButton.startAnimation();
                        new Handler().postDelayed(() -> register(), 1500);
                    }
            }
        }
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
        circularProgressButton.setOnClickListener(this);

        inputSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idSex = sexArrayList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idEducation = educationArrayList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog.dismiss();
    }

    @Override
    public void initValue() {

    }


    @Override
    public void getData() {
        progressDialog.show();
        ConnectServer.getInstance().get(educationListener, ConfigService.EDUCATION);
        ConnectServer.getInstance().get(sexListener, ConfigService.SEX);
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(RegisterCaretakerActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate() {

        if (!DateTHFormat.getInstance().isDateValid(getDob())) {
            inputDob.setError("กรุณาใส่วันเกิดให้ถูกต้อง");
            inputDob.requestFocus();
        }

        if (inputUsername.length() == 0) {
            inputUsername.setError("กรุณาใส่ชื่อผู้ใช้");
            inputUsername.requestFocus();
        }

        if (inputPassword.length() == 0) {
            inputPassword.setError("กรุณาใส่รหัสผ่าน");
            inputPassword.requestFocus();
        }

        if (inputConfirmPass.length() == 0) {
            inputConfirmPass.setError("กรุณาใส่ยืนยันรหัสผ่าน");
            inputConfirmPass.requestFocus();

        } else if (!(inputPassword.getText().toString().equals(inputConfirmPass.getText().toString()))) {
            inputConfirmPass.setError("กรุณาใส่รหัสผ่านให้ตรงกัน");
            inputConfirmPass.requestFocus();
        }

        if (inputFirstname.length() == 0) {
            inputFirstname.setError("กรุณาใส่ชื่อจริง");
            inputFirstname.requestFocus();
        }

        if (inputLastname.length() == 0) {
            inputLastname.setError("กรุณาใส่นามสกุล");
            inputLastname.requestFocus();
        }

        if (inputTell.length() == 0) {
            inputTell.setError("กรุณาใส่เบอร์โทรศัพท์");
            inputTell.requestFocus();
        } else if (inputTell.length() != 10) {
            inputTell.setError("เบอร์โทรศัพท์ไม่ถูกต้อง");
            inputTell.requestFocus();
        }

        if (inputEmail.length() == 0) {
            inputEmail.setError("กรุณาใส่อีเมลล์");
            inputEmail.requestFocus();
        }

        if (inputEmail.length() > 0)
            if (!isEmailValid(inputEmail.getText().toString())) {
                inputEmail.setError("อีเมลไม่ถูกต้อง");
                inputEmail.requestFocus();
            }


        return inputUsername.length() != 0 &&
                inputPassword.length() != 0 &&
                inputConfirmPass.length() != 0 &&
                (inputPassword.getText().toString().equals(inputConfirmPass.getText().toString())) &&
                inputFirstname.length() != 0 &&
                inputLastname.length() != 0 &&
                inputTell.length() == 10 &&
                inputTell.length() != 0 &&
                inputEmail.length() != 0 &&
                DateTHFormat.getInstance().isDateValid(getDob()) &&
                isEmailValid(inputEmail.getText().toString());
    }

    private String getDob() {
        String dobString;
        dobString = inputDay.getSelectedItem().toString() + " "
                + inputMonth.getSelectedItem().toString() + " "
                + inputYear.getSelectedItem().toString();
        return dobString;
    }

    private void register() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (imm != null) {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", inputUsername.getText().toString().trim());
        jsonObject.addProperty("password", inputPassword.getText().toString().trim());
        jsonObject.addProperty("titleName", "");
        jsonObject.addProperty("firstName", inputFirstname.getText().toString().trim());
        jsonObject.addProperty("lastName", inputLastname.getText().toString().trim());
        jsonObject.addProperty("sexId", idSex);
        jsonObject.addProperty("dob", getDob());
        jsonObject.addProperty("educationId", idEducation);
        jsonObject.addProperty("tell", inputTell.getText().toString().trim());
        jsonObject.addProperty("occupation", inputOccupation.getText().toString().trim());
        jsonObject.addProperty("email", inputEmail.getText().toString().trim());
        jsonObject.addProperty("deviceToken", DeviceToken.getInstance().getToken(RegisterCaretakerActivity.this));

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(final JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    int status = object.get("status").getAsInt();
                    if (status == 201) {
                        circularProgressButton.revertAnimation(() -> {
                            circularProgressButton.setText("สมัครสมาชิกสำเร็จ");
                            circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                            circularProgressButton.setBackgroundResource(R.drawable.shapebutton_complete);
                            new Handler().postDelayed(() -> {
                                Caretaker caretaker = CaretakerModel.getInstance().getCaretaker(object);
                                UserManager.getInstance(RegisterCaretakerActivity.this).storeCaretaker(caretaker);
                                Intent intent = new Intent(RegisterCaretakerActivity.this, ReHomeCaretakerActivity.class);
                                startActivity(intent);
                            }, 700);
                        });
                    } else {
                        circularProgressButton.revertAnimation(() -> {
                            circularProgressButton.setText(object.get("message").getAsString());
                            circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                            circularProgressButton.setBackgroundResource(R.drawable.shapebutton_error);
                            new Handler().postDelayed(() -> {
                                circularProgressButton.startAnimation();
                                new Handler().postDelayed(() -> {
                                    circularProgressButton.revertAnimation();
                                    circularProgressButton.setText("ตกลง");
                                    circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                    circularProgressButton.setBackgroundResource(R.drawable.shapetopics);
                                }, 1000);

                            }, 2000);
                        });
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
                NetworkUtil.isOnline(RegisterCaretakerActivity.this, circularProgressButton);
                new Handler().postDelayed(() -> {
                    circularProgressButton.startAnimation();
                    new Handler().postDelayed(() -> {
                        circularProgressButton.revertAnimation();
                        circularProgressButton.setText("ตกลง");
                        circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                        circularProgressButton.setBackgroundResource(R.drawable.shapetopics);
                    }, 1000);

                }, 2000);

            }
        }, ConfigService.CARETAKER, jsonObject);

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

