package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionNineteenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private Question question;
    private Question questionRef;
    private NumberQuestion numberQuestion;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_nineteen);
        getData();
        setUI();
        setListener();

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        radioGroup = findViewById(R.id.radio_group);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText(question.getTitle());
        initAnswer();
    }

    @Override
    public void getData() {
        numberQuestion = EvaluationModel.getInstance()
                .getEvaluationByNumber("7", QuestionNineteenActivity.this);
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("19", QuestionNineteenActivity.this)
                .getQuestion();
        questionRef = EvaluationModel.getInstance()
                .getEvaluationByNumber("7", QuestionNineteenActivity.this)
                .getQuestion();
    }

    private void initAnswer() {
        String answer = numberQuestion.getAnswerArrayList().get(0).getAnswer();
        answer += EvaluationModel.getInstance().getDummyChoiceCheckBox();
        String[] answerArray = answer.split(",");
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, answerArray);
        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            CheckBox checkBox = new CheckBox(QuestionNineteenActivity.this);
            checkBox.setText(list.get(i));
            radioGroup.addView(checkBox, i);
        }
    }

    private String getAnswer() {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) radioGroup.getChildAt(i);
            if (checkBox.isChecked()) {
                list.add(checkBox.getText().toString());
            }
        }
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i != list.size() - 1)
                builder.append(",");
        }
        return builder.toString();
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
                StoreAnswerTmse.getInstance().storeAnswerNineteen("19", question.getId(), questionRef.getId(), getAnswer());
                Intent intent = new Intent(QuestionNineteenActivity.this, ResultPassActivity.class);
                startActivity(intent);
            }

        }
    }
}