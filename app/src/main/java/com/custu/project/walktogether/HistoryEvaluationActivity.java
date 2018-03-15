package com.custu.project.walktogether;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.historyevaluation.EvaluationTest;
import com.custu.project.walktogether.data.historyevaluation.EvaluationTestSingle;
import com.custu.project.walktogether.data.historyevaluation.HistoryEvaluation;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.HistoryEvaluationModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.custu.project.walktogether.data.Evaluation.EvaluationCategory.EVALUATION_CATEGORY;

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
}