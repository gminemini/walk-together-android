package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ChoosemapActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemap);
        findViewById(R.id.map1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map","interPark");
                startActivity(intent);
            }
        });
        findViewById(R.id.map2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map","lc2");
                startActivity(intent);
            }
        });
        findViewById(R.id.map3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map","ekkami");
                startActivity(intent);
            }
        });

        findViewById(R.id.map4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoosemapActivity.this, MapsActivity.class);
                intent.putExtra("map","myHome");
                startActivity(intent);
            }
        });
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
