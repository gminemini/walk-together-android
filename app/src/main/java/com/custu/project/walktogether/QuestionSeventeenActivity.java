package com.custu.project.walktogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;

import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;

public class QuestionSeventeenActivity extends AppCompatActivity implements BasicActivity,View.OnClickListener{
    private DrawImage drawImage;
    private Button nextBtn;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_seventeen);
        getData();
        setUI();
    }

    @Override
    public void initValue() {
    }

    @Override
    public void setUI() {
        drawImage = findViewById(R.id.draw_line);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        drawImage.init(metrics);
        drawImage.normal();
        Button clearDrawLineButton = findViewById(R.id.delete_draw);
        clearDrawLineButton.setOnClickListener(this);
        nextBtn = (Button) findViewById(R.id.next);
        nextBtn.setOnClickListener(this);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText("(17) "+question.getTitle());
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("17", QuestionSeventeenActivity.this)
                .getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.delete_draw:
                drawImage.clear();
                break;
            case R.id.next:
                StoreAnswerTmse.getInstance().storeAnswer("no17", question.getId(), String.valueOf(true));
                Intent intent = new Intent(QuestionSeventeenActivity.this, QuestionEighteenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
