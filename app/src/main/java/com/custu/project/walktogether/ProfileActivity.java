package com.custu.project.walktogether;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.DialogQrCode;

public class ProfileActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private ImageView nextBtn;
    private Button logout;
    private Button qrCodeButton;
    private TextView patientNumber;

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getData();
        setUI();
        setListener();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                Intent intent = new Intent(ProfileActivity.this, EditprofileActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.logout: {
                UserManager.getInstance(ProfileActivity.this).removePatient();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.qrcode: {
                DialogQrCode.getInstance().showDialog(ProfileActivity.this, patient.getQrCode());
                break;
            }

        }
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
        logout.setOnClickListener(this);
        qrCodeButton.setOnClickListener(this);
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = findViewById(R.id.next);
        logout = findViewById(R.id.logout);
        qrCodeButton = findViewById(R.id.qrcode);
        patientNumber = findViewById(R.id.number);

        patientNumber.setText(patient.getPatientNumber());

    }

    @Override
    public void getData() {
        patient = UserManager.getInstance(ProfileActivity.this).getPatient();
    }

    @Override
    public void initProgressDialog() {

    }
}
