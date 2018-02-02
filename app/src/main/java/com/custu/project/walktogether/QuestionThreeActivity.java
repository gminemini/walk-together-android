package com.custu.project.walktogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.util.ArrayList;

public class QuestionThreeActivity extends AppCompatActivity implements BasicActivity {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_three);


        setUI();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
    }

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    private void createSpinnerData() {

        answerArray.add("มกราคม");
        answerArray.add("กุมภาพันธ์ ");
        answerArray.add("มีนาคม");
        answerArray.add("เมษายน");
        answerArray.add("พฤษภาคม");
        answerArray.add("มิถุนายน");
        answerArray.add("กรกฎาคม");
        answerArray.add("สิงหาคม");
        answerArray.add("กันยายน");
        answerArray.add("ตุลาคม");
        answerArray.add("พฤศจิกายน");
        answerArray.add("ธันวาคม");

    }
}
