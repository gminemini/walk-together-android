package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class MainActivity extends Activity  implements BasicActivity {
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("DSs", "onCreate: ");

        setUI();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseuserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initValue() {

    }

    public void setUI() {
        registerBtn = (Button)findViewById(R.id.register);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

}
