package com.custu.project.walktogether;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class HomeActivity extends Activity implements BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }
}
