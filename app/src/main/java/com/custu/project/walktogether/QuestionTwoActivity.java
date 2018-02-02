package com.custu.project.walktogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.custu.project.project.walktogether.R;

import java.util.ArrayList;

public class QuestionTwoActivity extends AppCompatActivity {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_two);


        setUI();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
    }

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
    }

    private void createSpinnerData() {

        answerArray.add("วันอาทิตย์");
        answerArray.add("วันจันทร์");
        answerArray.add("วันอังคาร");
        answerArray.add("วันพุธ");
        answerArray.add("วันพฤหัสบดี");
        answerArray.add("วันศุกร์");
        answerArray.add("วันเสาร์");

    }
}
