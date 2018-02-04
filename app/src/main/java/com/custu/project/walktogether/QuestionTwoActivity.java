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

public class QuestionTwoActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Spinner answerSpinner;
    private ArrayList<Integer> answerArray = new ArrayList<Integer>();
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_two);


        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<Integer> adapterArray = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);

    }


    private void createSpinnerData() {
        for (int i = 1; i < 32; i++) {
            answerArray.add(i);
        }
    }

    @Override
    public void initValue() {

    }

    @Override
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

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                Intent intent = new Intent(QuestionTwoActivity.this, QuestionThreeActivity.class);
                startActivity(intent);
            }

        }
    }
}
