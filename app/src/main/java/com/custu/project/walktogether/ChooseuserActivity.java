package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ChooseuserActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private boolean checked;
    private Button nextBtn;
    private String checktype = "";
    private TextView patientBtn, careBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseuser);
        setUI();
        setListener();

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        patientBtn = (TextView) findViewById(R.id.patientbtn);
        careBtn = (TextView) findViewById(R.id.caretakerbtn);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        patientBtn.setOnClickListener(this);
        careBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.patientbtn: {
                Intent intent = new Intent(ChooseuserActivity.this, ConditionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.caretakerbtn: {
                Intent intent = new Intent(ChooseuserActivity.this, RegisterCaretakerActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
