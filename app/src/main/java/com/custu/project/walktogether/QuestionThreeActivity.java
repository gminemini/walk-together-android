package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class QuestionThreeActivity extends AppCompatActivity implements BasicActivity,View.OnClickListener {
    private Spinner answerSpinner;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private Button nextBtn;
    private TextView titleTextView;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_three);
        getData();
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
        titleTextView = findViewById(R.id.title);
        titleTextView.setText(question.getTitle());
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("3", QuestionThreeActivity.this).getQuestion();
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
                StoreAnswerTmse.getInstance().storeAnswer("no3", question.getId(), answerSpinner.getSelectedItem().toString());
                Intent intent = new Intent(QuestionThreeActivity.this, QuestionFourActivity.class);
                startActivity(intent);
            }

        }
    }
}
