package com.custu.project.walktogether;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;

public class QuestionNineActivity extends AppCompatActivity implements BasicActivity {
    private Button nextBtn;
    private EditText inputTopicFour;
    private Question question;
    private int count = 0;
    private int resultScore = 0;
    private TextView title;
    private TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_nine);
        getData();
        setUI();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(QuestionNineActivity.this);

            }
        });
    }

    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);
        titleTextView.setText(inputTopicFour.getText() + " " + titleTextView.getText());

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                resultScore += getScore();
                if (count == 3) {
                    StoreAnswerTmse.getInstance().storeAnswer("9", question.getId(), String.valueOf(resultScore));
                    Intent intent = new Intent(QuestionNineActivity.this, QuestionTwelveActivity.class);
                    dialog.dismiss();
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    nextQuestion(inputTopicFour.getText().toString());
                }
            }
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                inputTopicFour.setText("");
            }
        });
        dialog.show();
    }

    private void nextQuestion(String input) {
        questionTextView.setText(input);
        inputTopicFour.setText("");
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputTopicFour = (EditText) findViewById(R.id.input_topicfour);
        title = findViewById(R.id.title);
        questionTextView = findViewById(R.id.question);
        TextView description = findViewById(R.id.description);
        title.setText(question.getTitle());
        description.setText(question.getDescription());
    }

    private int getScore() {
        int minus = Integer.parseInt(question.getDescription());
        int question = Integer.parseInt(questionTextView.getText().toString());
        int answer = Integer.parseInt(inputTopicFour.getText().toString());
        return ((question - minus) == answer) ? 1 : 0;
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("9", QuestionNineActivity.this).getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }
}
