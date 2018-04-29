package com.custu.project.walktogether.controller.patient;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.master.Education;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MasterModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.custu.project.walktogether.util.DeviceToken;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.InitSpinnerDob;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.SwipeBack;
import com.google.gson.JsonObject;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class RegisterPatientActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Calendar calendar;
    private Button nextBtn;
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputConfirmPass;
    private EditText inputTitlename;
    private EditText inputFirstname;
    private EditText inputLastname;
    private EditText inputDob;
    private Spinner inputSex;
    private Spinner inputEducation;
    private EditText inputTell;
    private EditText inputOccupation;
    private EditText inputEmail;
    private ProgressDialog progressDialog;
    private CircularProgressButton circularProgressButton;

    private Spinner inputDay;
    private Spinner inputMonth;
    private Spinner inputYear;

    private ArrayList<Sex> sexArrayList = new ArrayList<>();
    private ArrayList<Education> educationArrayList = new ArrayList<>();

    private Long idSex;
    private Long idEducation;
    private Long idPatient;

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
                setEducation();
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
        setContentView(R.layout.register_patient);
        calendar = Calendar.getInstance();
        initProgressDialog();
        getData();
        setUI();
        setListener();
        Slidr.attach(this, SwipeBack.getInstance().confrig());
    }

    private void setSexSpinner() {
        ArrayAdapter<Sex> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexArrayList);
        inputSex.setAdapter(adapterArray);
        inputSex.setSelection(0);
    }

    private void setEducation() {
        ArrayAdapter<Education> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, educationArrayList);
        inputEducation.setAdapter(adapterArray);
        inputEducation.setSelection(0);
    }

    @Override
    public void setUI() {
        nextBtn = findViewById(R.id.next);
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPass = findViewById(R.id.input_confirm_pass);
        inputTitlename = findViewById(R.id.input_titlename);
        inputFirstname = findViewById(R.id.input_firstname);
        inputLastname = findViewById(R.id.input_lastname);
        inputDob = findViewById(R.id.input_dob);
        inputSex = findViewById(R.id.input_sex);
        inputTell = findViewById(R.id.input_tell);
        inputOccupation = findViewById(R.id.input_occupation);
        inputEmail = findViewById(R.id.input_email);
        inputEducation = findViewById(R.id.input_education);
        circularProgressButton = findViewById(R.id.register_button);
        circularProgressButton.setBackgroundResource(R.drawable.shapetopics);

        inputDay = findViewById(R.id.day);
        inputMonth = findViewById(R.id.month);
        inputYear = findViewById(R.id.year);
        setDobSpinner();
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_dob: {
                DatePickerDialog datePickerIssue = new DatePickerDialog(RegisterPatientActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(inputDob);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerIssue.show();
                break;
            }
            case R.id.next: {
                if (validate()) {
                    register();
                }
            }
            case R.id.register_button: {
                if (NetworkUtil.isOnline(RegisterPatientActivity.this, circularProgressButton))
                    if (validate()) {
                        circularProgressButton.startAnimation();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                register();
                            }
                        }, 1500);
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

    }

    private void updateLabel(EditText editText) {
        editText.setText(DateTHFormat.getInstance().normalDateFormatPlus(calendar.getTime()));
    }

    @Override
    public void initValue() {

    }


    @Override
    public void getData() {
        ConnectServer.getInstance().get(sexListener, ConfigService.SEX);
        ConnectServer.getInstance().get(educationListener, ConfigService.EDUCATION);
        idPatient = getIntent().getLongExtra("idPatient", 0);
        if (getIntent().getBooleanExtra("isContinue", false))
            DialogUtil.getInstance().showDialogStartIntent(RegisterPatientActivity.this, "ดำเนินการสมัครให้เสร็จสิ้น");
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(RegisterPatientActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private String getDob() {
        String dobString;
        dobString = inputDay.getSelectedItem().toString() + " "
                + inputMonth.getSelectedItem().toString() + " "
                + inputYear.getSelectedItem().toString();
        return dobString;
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


        if (inputTitlename.length() == 0) {
            inputTitlename.setError("กรุณาใส่คำนำหน้าชื่อ");
            inputTitlename.requestFocus();
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
                inputTitlename.length() != 0 &&
                inputFirstname.length() != 0 &&
                inputLastname.length() != 0 &&
                inputTell.length() == 10 &&
                inputTell.length() != 0 &&
                inputEmail.length() != 0 &&
                DateTHFormat.getInstance().isDateValid(getDob()) &&
                isEmailValid(inputEmail.getText().toString());
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
        jsonObject.addProperty("idPatient", idPatient);
        jsonObject.addProperty("userName", inputUsername.getText().toString().trim());
        jsonObject.addProperty("password", inputPassword.getText().toString().trim());
        jsonObject.addProperty("titleName", inputTitlename.getText().toString().trim());
        jsonObject.addProperty("firstName", inputFirstname.getText().toString().trim());
        jsonObject.addProperty("lastName", inputLastname.getText().toString().trim());
        jsonObject.addProperty("sexId", idSex);
        jsonObject.addProperty("dob", getDob());
        jsonObject.addProperty("educationId", idEducation);
        jsonObject.addProperty("tell", inputTell.getText().toString().trim());
        jsonObject.addProperty("occupation", inputOccupation.getText().toString().trim());
        jsonObject.addProperty("email", inputEmail.getText().toString().trim());
        jsonObject.addProperty("deviceToken", DeviceToken.getInstance().getToken(RegisterPatientActivity.this));

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(final JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    int status = object.get("status").getAsInt();
                    if (status == 201) {
                        circularProgressButton.revertAnimation(new OnAnimationEndListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onAnimationEnd() {
                                circularProgressButton.setText("สมัครสมาชิกสำเร็จ");
                                circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                circularProgressButton.setBackgroundResource(R.drawable.shapebutton_complete);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Patient patient = PatientModel.getInstance().getPatient(object);
                                        UserManager.getInstance(RegisterPatientActivity.this).storePatient(patient);
                                        Intent intent = new Intent(RegisterPatientActivity.this, ReHomePatientActivity.class);
                                        startActivity(intent);
                                    }
                                }, 700);
                            }
                        });
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
                                                circularProgressButton.setText("ตกลง");
                                                circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                                circularProgressButton.setBackgroundResource(R.drawable.shapetopics);
                                            }
                                        }, 1000);

                                    }
                                }, 2000);
                            }
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
                NetworkUtil.isOnline(RegisterPatientActivity.this, circularProgressButton);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        circularProgressButton.startAnimation();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                circularProgressButton.revertAnimation();
                                circularProgressButton.setText("ตกลง");
                                circularProgressButton.setTextColor(Color.parseColor("#FFFFFF"));
                                circularProgressButton.setBackgroundResource(R.drawable.shapetopics);
                            }
                        }, 1000);

                    }
                }, 2000);
            }
        }, ConfigService.PATIENT, jsonObject);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}

