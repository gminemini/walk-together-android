package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ChoosemapActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemap);
        initProgressDialog();
        findViewById(R.id.map1).setOnClickListener(this);
        findViewById(R.id.map2).setOnClickListener(this);
        findViewById(R.id.map3).setOnClickListener(this);
        findViewById(R.id.map4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        progressDialog.show();
        Intent intent;
        switch (id) {
            case R.id.map1:
                intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map", "interPark");
                startActivity(intent);
                break;
            case R.id.map2:
                intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map", "lc2");
                startActivity(intent);
                break;
            case R.id.map3:
                intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map", "ekkami");
                startActivity(intent);
                break;
            case R.id.map4:
                intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map", "myHome");
                startActivity(intent);
                break;
        }

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
        progressDialog = new ProgressDialog(ChoosemapActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }
}
