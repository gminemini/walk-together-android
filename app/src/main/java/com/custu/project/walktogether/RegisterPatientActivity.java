package com.custu.project.walktogether;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    private EditText inputPostcode;
    private Spinner inputSex;
    private EditText inputAddress;
    private Spinner inputProvince;
    private Spinner inputDistric;
    private Spinner inputSubdistric;
    private EditText inputTell;
    private Spinner inputOccupation;
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
        setContentView(R.layout.register_patient);

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
        nextBtn = (Button)findViewById(R.id.next);
        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPass = (EditText) findViewById(R.id.input_confirm_pass);
        inputTitlename = (EditText) findViewById(R.id.input_titlename);
        inputFirstname = (EditText) findViewById(R.id.input_firstname);
        inputLastname = (EditText) findViewById(R.id.input_lastname);
        inputDob = (EditText) findViewById(R.id.input_dob);
        inputSex = (Spinner) findViewById(R.id.input_sex);
        inputAddress = (EditText) findViewById(R.id.input_address);
        inputProvince = (Spinner) findViewById(R.id.input_province);
        inputDistric = (Spinner) findViewById(R.id.input_distric);
        inputSubdistric = (Spinner) findViewById(R.id.input_subdistric);
        inputTell = (EditText) findViewById(R.id.input_tell);
        inputOccupation = (Spinner) findViewById(R.id.input_occupation);
        inputEmail = (EditText) findViewById(R.id.input_email);

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
                boolean r = validate();

                if (validate()) {
                    Intent intent = new Intent(RegisterPatientActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {

                }
            }

        }
    }

    private void setListener() {
        inputDob.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

    }

    private void updateLabel(EditText editText) {
        editText.setText(DateTHFormat.getInstance().normalDateFormat(calendar.getTime()));
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
        progressDialog = new ProgressDialog(RegisterPatientActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
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

        if (inputEmail.length() == 0)
            inputEmail.setError("กรุณาใส่อีเมล");
        else if (!isEmailValid(inputEmail.getText().toString()))
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
                inputTitlename.length()!=0 &&
                inputTell.length() == 10 &&
                inputTell.length()!=0 &&
                inputEmail.length()!=0 &&
                isEmailValid(inputEmail.getText().toString()) ;


    }
}

