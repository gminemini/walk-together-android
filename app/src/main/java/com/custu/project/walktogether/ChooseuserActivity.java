package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ChooseuserActivity extends Activity implements BasicActivity {
    private boolean checked;
    private Button nextBtn;
    private String checktype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseuser);
        setUI();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checktype == "1"){
                    Intent intent = new Intent(ChooseuserActivity.this, TopicsOneActivity.class);
                    startActivity(intent);
                }
                else if (checktype == "2"){
                    Intent intent = new Intent(ChooseuserActivity.this, RegisterCaretakerActivity.class);
                    startActivity(intent);
                }else{

                }

            }
        });
    }


    public void onRadioButtonClicked(View view) {
        checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_patient:
                if (checked) {
                        checktype = "1";
                }

                break;
            case R.id.radio_care:
                if (checked) {
                    checktype = "2";
                }

                break;
        }
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
}
