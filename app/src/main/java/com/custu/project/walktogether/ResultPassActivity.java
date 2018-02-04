package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ResultPassActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pass);
        setUI();
        setListener();

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                Intent intent = new Intent(ResultPassActivity.this, RegisterPatientActivity.class);
                startActivity(intent);
            }

        }
    }
}
