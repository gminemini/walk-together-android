package com.custu.project.walktogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.util.ArrayList;

public class QuestionFourActivity extends AppCompatActivity implements BasicActivity {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_four);


        setUI();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
    }

    @Override
    public void initValue() {

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

        answerArray.add("เช้ามืด");
        answerArray.add("เช้า ");
        answerArray.add("สาย");
        answerArray.add("เที่ยง");
        answerArray.add("บ่าย");
        answerArray.add("เย็น");
        answerArray.add("หัวค่ำ");
        answerArray.add("กลางคื");


    }
}
