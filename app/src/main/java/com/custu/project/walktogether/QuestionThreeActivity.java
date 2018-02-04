package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.util.ArrayList;

public class QuestionThreeActivity extends AppCompatActivity implements BasicActivity,View.OnClickListener {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_three);


        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);

    }

    @Override
    public void initValue() {

    }

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
        nextBtn = (Button) findViewById(R.id.next);
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
    private void setListener() {
        nextBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                Intent intent = new Intent(QuestionThreeActivity.this, QuestionFourActivity.class);
                startActivity(intent);
            }

        }
    }
}
