package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class MainActivity extends Activity  implements BasicActivity ,View.OnClickListener{
    private Button registerBtn;
    private Button loginBtn;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("DSs", "onCreate: ");

        setUI();
        setListener();
    }

    @Override
    public void initValue() {

    }

    private void setListener() {
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register: {
                Intent intent = new Intent(MainActivity.this, ChooseuserActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.login: {
                if (validate()) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

        }
    }
    private boolean validate() {

        if (username.length() == 0)
            username.setError("Please enter name");

        if (password.length() == 0)
            username.setError("Please enter lastname");

        return username.length() != 0 &&  password.length() != 0 ;

    }

    @Override
    public void setUI() {
        registerBtn = (Button)findViewById(R.id.register);
        loginBtn = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.input_username);
        password = (EditText)findViewById(R.id.input_password);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

}
