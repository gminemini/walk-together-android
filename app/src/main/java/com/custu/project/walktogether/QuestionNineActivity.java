package com.custu.project.walktogether;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionNineActivity extends AppCompatActivity implements BasicActivity {
    private Button nextBtn;
    private EditText inputTopicFour;
    private Question question;
    private int count = 0;
    private int numberQ = 9;
    private int resultScore = 0;
    private int questionNext = 0;
    private TextView questionTextView;
    private TextView numberQTextView;

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
        countDownTime();
    }

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
        final int[] time = {21};
        final ProgressBar progress;
        progress = findViewById(R.id.progress);
        progress.setMax(time[0]);
        progress.setProgress(time[0]);
        countDownTimer = new CountDownTimer(timeInterval, 1000) {
            public void onTick(long millisUntilFinished) {
                progress.setProgress(--time[0]);
            }

            public void onFinish() {
                progress.setProgress(0);
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no9", question.getId(), "");
                Intent intent = new Intent(QuestionNineActivity.this, QuestionTwelveActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
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
                    countDownTimer.cancel();
                    StoreAnswerTmse.getInstance().storeAnswer("no9", question.getId(), String.valueOf(resultScore));
                    Intent intent = new Intent(QuestionNineActivity.this, QuestionTwelveActivity.class);
                    dialog.dismiss();
                    startActivity(intent);
                } else {
                    numberQ++;
                    dialog.dismiss();
                    nextQuestion(String.valueOf(questionNext));
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
        numberQTextView.setText("(" + numberQ + ") ");
        inputTopicFour.setText("");
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        inputTopicFour = (EditText) findViewById(R.id.input_topicfour);
        numberQTextView = (TextView) findViewById(R.id.numberQ);
        numberQTextView.setText("(" + numberQ + ") ");
        questionTextView = findViewById(R.id.question);
        TextView description = findViewById(R.id.description);
        description.setText(question.getDescription());
    }

    private int getScore() {
        int answer;
        int minus = Integer.parseInt(question.getDescription());
        int question = Integer.parseInt(questionTextView.getText().toString());
        if (inputTopicFour.getText().toString().length() == 0) {
            answer = Integer.parseInt("0");
            questionNext = question - minus;
        } else {
            answer = Integer.parseInt(inputTopicFour.getText().toString());
            questionNext = answer;
        }
        return ((question - minus) == answer) ? 1 : 0;
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("9", QuestionNineActivity.this).getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
