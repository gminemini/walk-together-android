package com.custu.project.walktogether.controller.tmse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.patient.ReHomePatientActivity;
import com.custu.project.walktogether.controller.LoginActivity;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setUI();
        setListener();
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                if (UserManager.getInstance(ResultActivity.this).getPatient() != null) {
                    Intent intent = new Intent(ResultActivity.this, ReHomePatientActivity.class);
                    intent.putExtra("page", "historyEvaluation");
                    startActivity(intent);
                    break;
                } else {
                    Intent intent = new Intent(ResultActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                }
            }

        }
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        TextView scoreTextView = findViewById(R.id.result_score);
        scoreTextView.setText(String.valueOf(getIntent().getIntExtra("score", 0)));
        nextBtn = (Button) findViewById(R.id.next);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
