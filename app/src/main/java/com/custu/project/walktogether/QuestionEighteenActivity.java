package com.custu.project.walktogether;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class QuestionEighteenActivity extends AppCompatActivity implements BasicActivity {
    private Button nextBtn;
    private EditText inputEditText;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_eighteen);
        getData();
        setUI();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(QuestionEighteenActivity.this);

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
        titleTextView.setText(inputEditText.getText().toString());

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreAnswerTmse.getInstance().storeAnswer("18", question.getId(), inputEditText.getText().toString());
                Intent intent = new Intent(QuestionEighteenActivity.this, QuestionNineteenActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                inputEditText.setText("");
            }
        });
        dialog.show();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputEditText = (EditText) findViewById(R.id.input_topicfive);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText(question.getTitle() + "เหมือนกันคือ");
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("18", QuestionEighteenActivity.this)
                .getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }
}
