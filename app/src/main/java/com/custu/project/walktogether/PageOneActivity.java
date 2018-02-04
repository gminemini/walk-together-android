package com.custu.project.walktogether;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class PageOneActivity extends AppCompatActivity implements BasicActivity,View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.page_one);

    }




    @Override
    public void onClick(View v) {

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
