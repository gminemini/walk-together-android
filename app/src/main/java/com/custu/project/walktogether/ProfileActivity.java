package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

public class ProfileActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private ImageView nextBtn;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        }
    }
    private void setListener() {
        nextBtn.setOnClickListener(this);
        logout.setOnClickListener(this);

    }
    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (ImageView) findViewById(R.id.next);
        logout = (Button) findViewById(R.id.logout);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }
}
