package com.custu.project.walktogether;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.DateTHFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    private Spinner inputSex;
    private EditText inputAddress;
    private Spinner inputProvince;
    private Spinner inputDistric;
    private Spinner inputSubdistric;
    private EditText inputTell;
    private Spinner inputOccupation;
    private EditText inputEmail;
    private ArrayList<String> sexArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_caretaker);

        calendar = Calendar.getInstance();

        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sexArray);
        inputSex.setAdapter(adapterArray);
    }

    private void createSpinnerData() {

        sexArray.add("ชาย");
        sexArray.add("หญิง");


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
                boolean r = validate();
                Log.d("sd", "onClick: "+r);
                if (validate()) {
                    Intent intent = new Intent(RegisterCaretakerActivity.this, HomecaretakerActivity.class);
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

    }

    @Override
    public void initProgressDialog() {

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

