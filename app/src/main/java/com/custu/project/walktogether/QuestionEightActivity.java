package com.custu.project.walktogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

import java.util.ArrayList;

public class QuestionEightActivity extends AppCompatActivity implements BasicActivity {
    private Spinner answerSpinnerOne;
    private Spinner answerSpinnerTwo;
    private Spinner answerSpinnerThree;
    private Spinner answerSpinnerFour;
    private Spinner answerSpinnerFive;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private ArrayAdapter<String> adapterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_eight);


        setUI();
        createSpinnerData();
        adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray);
        setAdapter();
    }

    public void setUI() {
        answerSpinnerOne = (Spinner) findViewById(R.id.answer_day_one);
        answerSpinnerTwo = (Spinner) findViewById(R.id.answer_day_two);
        answerSpinnerThree = (Spinner) findViewById(R.id.answer_day_three);
        answerSpinnerFour = (Spinner) findViewById(R.id.answer_day_four);
        answerSpinnerFive = (Spinner) findViewById(R.id.answer_day_five);


    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    public void setAdapter() {
        answerSpinnerOne.setAdapter(adapterArray);
        answerSpinnerTwo.setAdapter(adapterArray);
        answerSpinnerThree.setAdapter(adapterArray);
        answerSpinnerFour.setAdapter(adapterArray);
        answerSpinnerFive.setAdapter(adapterArray);


        adapterArray.setDropDownViewResource(R.layout.spinner_layout);
        answerSpinnerOne.setAdapter(adapterArray);

    }

    private void createSpinnerData() {

        answerArray.add("วันจันทร์");
        answerArray.add("วันอาทิตย์");
        answerArray.add("วันพุธ");
        answerArray.add("วันอังคาร");
        answerArray.add("วันศุกร์");
        answerArray.add("วันพฤหัสบดี");
        answerArray.add("วันเสาร์");


    }

}
