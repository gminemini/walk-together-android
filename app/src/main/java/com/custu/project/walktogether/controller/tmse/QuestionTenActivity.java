package com.custu.project.walktogether.controller.tmse;

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
import com.custu.project.walktogether.util.BasicActivity;

public class QuestionTenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private EditText input_topicfour;
    private Intent intent;
    private String ans;
    private TextView question_ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_ten);
        getData();
        setUI();
        setListener();
        nextBtn.setOnClickListener(v -> showDialog(QuestionTenActivity.this));
    }

    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);


        titleTextView.setText(input_topicfour.getText() + " " + titleTextView.getText());


        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionTenActivity.this, QuestionElevenActivity.class);
            dialog.dismiss();
            intent.putExtra("EXTRA_ANS", input_topicfour.getText().toString());
            startActivity(intent);
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        input_topicfour = (EditText) findViewById(R.id.input_topicfour);
        question_ten = (TextView) findViewById(R.id.question_ten);
        question_ten.setText(ans + " - 7 = ? ");

    }

    @Override
    public void getData() {
        ans = getIntent().getStringExtra("EXTRA_ANS");
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
