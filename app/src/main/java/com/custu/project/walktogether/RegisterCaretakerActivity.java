package com.custu.project.walktogether;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.master.District;
import com.custu.project.walktogether.data.master.Province;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.data.master.SubDistrict;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MasterModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.custu.project.walktogether.util.ProgressDialogCustom;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class RegisterCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Calendar calendar;
    private Button nextBtn;
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputConfirmPass;
    private EditText inputTitlename;
    private EditText inputFirstname;
    private EditText inputLastname;
    private EditText inputDob;
    private EditText inputPostcode;
    private Spinner inputSex;
    private EditText inputAddress;
    private Spinner inputProvince;
    private Spinner inputDistric;
    private Spinner inputSubdistric;
    private EditText inputTell;
    private EditText inputOccupation;
    private EditText inputEmail;
    private ProgressDialog progressDialog;
    private ProgressBar districtProgressBar;
    private ProgressBar subDistrictProgressBar;
    private LinearLayout districtLinearLayout;
    private LinearLayout subDistrictLinearLayout;

    private ArrayList<Sex> sexArrayList = new ArrayList<>();
    private ArrayList<Province> provinceArrayList = new ArrayList<>();
    private ArrayList<District> districtArrayList = new ArrayList<>();
    private ArrayList<SubDistrict> subDistrictArrayList = new ArrayList<>();

    private Long idSex;
    private Long idProvince;
    private Long idDistrict;
    private Long idSubDistrict;

    private boolean iSSex = false;
    private boolean iSProvince = false;
    private boolean iSDistrict = false;
    private boolean iSSubDistrict = false;

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

    OnDataSuccessListener provinceListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            if (object != null) {
                provinceArrayList = MasterModel.getInstance().getProvince(object);
                setProvinceSpinner();
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

    OnDataSuccessListener districtListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            if (object != null) {
                districtArrayList = MasterModel.getInstance().getDistrict(object);
                setDistrictSpinner();
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

    OnDataSuccessListener subDistrictListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            if (object != null) {
                subDistrictArrayList = MasterModel.getInstance().getSubDistrict(object);
                setSubDistrictSpinner();
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
        calendar = Calendar.getInstance();
        initProgressDialog();
        getData();
        setUI();
        setListener();
    }

    private void setSexSpinner() {
        ArrayAdapter<Sex> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexArrayList);
        inputSex.setAdapter(adapterArray);
    }

    private void setProvinceSpinner() {
        ArrayAdapter<Province> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, provinceArrayList);
        inputProvince.setAdapter(adapterArray);
    }

    private void setDistrictSpinner() {
        ArrayAdapter<District> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, districtArrayList);
        inputDistric.setAdapter(adapterArray);
        districtProgressBar.setVisibility(View.GONE);
        districtLinearLayout.setVisibility(View.VISIBLE);

    }

    private void setSubDistrictSpinner() {
        ArrayAdapter<SubDistrict> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, subDistrictArrayList);
        inputSubdistric.setAdapter(adapterArray);
        subDistrictProgressBar.setVisibility(View.GONE);
        subDistrictLinearLayout.setVisibility(View.VISIBLE);
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
        inputAddress = findViewById(R.id.input_address);
        inputProvince = findViewById(R.id.input_province);
        inputDistric = findViewById(R.id.input_distric);
        inputSubdistric = findViewById(R.id.input_subdistric);
        inputTell = findViewById(R.id.input_tell);
        inputOccupation = findViewById(R.id.input_occupation);
        inputEmail = findViewById(R.id.input_email);
        districtProgressBar = findViewById(R.id.progress_district);
        subDistrictProgressBar = findViewById(R.id.progress_subdistrict);
        districtLinearLayout = findViewById(R.id.layout_district);
        subDistrictLinearLayout = findViewById(R.id.layout_subdistrict);
        inputPostcode = findViewById(R.id.postcode);

        inputProvince.setSelected(false);
        inputProvince.setSelection(0, true);

        inputDistric.setSelected(false);
        inputDistric.setSelection(0, true);

        inputSubdistric.setSelected(false);
        inputSubdistric.setSelection(0, true);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_dob: {
                DatePickerDialog datePickerIssue = new DatePickerDialog(RegisterCaretakerActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    register();/*
                    Intent intent = new Intent(RegisterCaretakerActivity.this, HomecaretakerActivity.class);
                    startActivity(intent);*/
                }
            }
        }
    }

    private void setListener() {
        inputDob.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        inputProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtProgressBar.setVisibility(View.VISIBLE);
                districtLinearLayout.setVisibility(View.GONE);
                idProvince = provinceArrayList.get(i).getId();
                ConnectServer.getInstance().get(districtListener, ConfigService.DISTRICT + idProvince);
                iSProvince = true;
                iSDistrict = false;
                iSSubDistrict = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputDistric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subDistrictProgressBar.setVisibility(View.VISIBLE);
                subDistrictLinearLayout.setVisibility(View.GONE);
                idDistrict = districtArrayList.get(i).getId();
                ConnectServer.getInstance().get(subDistrictListener, ConfigService.SUB_DISTRICT + idDistrict);
                iSDistrict = true;
                iSSubDistrict = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputSubdistric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idSubDistrict = subDistrictArrayList.get(i).getId();
                iSSubDistrict = true;
                inputPostcode.setText(subDistrictArrayList.get(i).getZipCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idSex = sexArrayList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog.dismiss();
    }

    private void updateLabel(EditText editText) {
        editText.setText(DateTHFormat.getInstance().normalDateFormatPlus(calendar.getTime()));
    }

    @Override
    public void initValue() {

    }


    @Override
    public void getData() {
        progressDialog.show();
        ConnectServer.getInstance().get(provinceListener, ConfigService.PROVINCE);
        ConnectServer.getInstance().get(sexListener, ConfigService.SEX);
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(RegisterCaretakerActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate() {

        if (inputUsername.length() == 0)
            inputUsername.setError("กรุณาใส่ชื่อผู้ใช้");

        if (inputPassword.length() == 0)
            inputPassword.setError("กรุณาใส่รหัสผ่าน");

        if (inputConfirmPass.length() == 0)
            inputConfirmPass.setError("กรุณาใส่ยืนยันรหัสผ่าน");

        else if (!(inputPassword.getText().toString().equals(inputConfirmPass.getText().toString())))
            inputConfirmPass.setError("กรุณาใส่รหัสผ่านให้ตรงกัน");


        if (inputTitlename.length() == 0)
            inputTitlename.setError("กรุณาใส่คำนำหน้าชื่อ");
        if (inputFirstname.length() == 0)
            inputFirstname.setError("กรุณาใส่ชื่อจริง");
        if (inputLastname.length() == 0)
            inputLastname.setError("กรุณาใส่นามสกุล");


        if (inputDob.length() == 0)
            inputDob.setError("กรุณาใส่วันเกิด");


        if (inputAddress.length() == 0)
            inputAddress.setError("กรุณาใส่ที่อยู่");

        if (inputTell.length() == 0)
            inputTell.setError("กรุณาใส่เบอร์โทรศัพท์");
        else if (inputTell.length() != 10)
            inputTell.setError("เบอร์โทรศัพท์ไม่ถูกต้อง");

        if (inputEmail.length() > 0)
            if (!isEmailValid(inputEmail.getText().toString()))
                inputEmail.setError("อีเมลไม่ตถูกต้อง");


        return inputUsername.length() != 0 &&
                inputPassword.length() != 0 &&
                inputConfirmPass.length() != 0 &&
                (inputPassword.getText().toString().equals(inputConfirmPass.getText().toString())) &&
                inputTitlename.length() != 0 &&
                inputFirstname.length() != 0 &&
                inputLastname.length() != 0 &&
                inputDob.length() != 0 &&
                inputAddress.length() != 0 &&
                inputTitlename.length() != 0 &&
                inputTell.length() == 10 &&
                inputTell.length() != 0 &&
                (inputEmail.length() == 0 || isEmailValid(inputEmail.getText().toString()));

    }

    private void register() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", inputUsername.getText().toString().trim());
        jsonObject.addProperty("password", inputPassword.getText().toString().trim());
        jsonObject.addProperty("titleName", inputTitlename.getText().toString().trim());
        jsonObject.addProperty("firstName", inputFirstname.getText().toString().trim());
        jsonObject.addProperty("lastName", inputLastname.getText().toString().trim());
        jsonObject.addProperty("sexId", idSex);
        jsonObject.addProperty("dob", inputDob.getText().toString().trim());
        jsonObject.addProperty("address", inputAddress.getText().toString().trim());
        jsonObject.addProperty("provinceId", idProvince);
        jsonObject.addProperty("districtId", idDistrict);
        jsonObject.addProperty("subDistrictId", idSubDistrict);
        jsonObject.addProperty("tell", inputTell.getText().toString().trim());
        jsonObject.addProperty("occupation", inputOccupation.getText().toString().trim());
        jsonObject.addProperty("email", inputEmail.getText().toString().trim());

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object!=null) {
                    int status = object.get("status").getAsInt();
                    if (status == 201) {
                        Log.d("onResponse: ", "onResponse: "+ object);
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
        }, ConfigService.CARETAKER, jsonObject);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}

