package com.custu.project.walktogether.controller.patient;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.patient.EvaluationHistoryFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryEvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putLong("idPatient", getIntent().getLongExtra("idPatient", 0));
            EvaluationHistoryFragment historyEvaluationFragment = new EvaluationHistoryFragment();
            historyEvaluationFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.container, historyEvaluationFragment).commit();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}