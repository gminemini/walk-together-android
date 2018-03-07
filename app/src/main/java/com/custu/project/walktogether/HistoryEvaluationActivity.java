package com.custu.project.walktogether;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.EvaluationCategory;
import com.custu.project.walktogether.data.historyevaluation.EvaluationTest;
import com.custu.project.walktogether.data.historyevaluation.EvaluationTestSingle;
import com.custu.project.walktogether.data.historyevaluation.HistoryEvaluation;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.model.HistoryEvaluationModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
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
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private LineChartView chart;
        private LineChartData data;

        private ArrayList<HistoryEvaluation> historyEvaluations = new ArrayList<>();
        private ArrayList<EvaluationTestSingle> evaluationTestSingles = new ArrayList<>();
        private float minScore = Float.MAX_VALUE;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

            chart = rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            chart.setViewportCalculationEnabled(false);
            getData();
            return rootView;
        }

        private void getData() {
            ConnectServer.getInstance().get(new OnDataSuccessListener() {
                @Override
                public void onResponse(JsonObject object, Retrofit retrofit) {
                    if (object != null) {
                        historyEvaluations = HistoryEvaluationModel.getInstance().getHistoryEvaluations(object);
                        generateData();
                    }

                }

                @Override
                public void onBodyError(ResponseBody responseBodyError) {

                }

                @Override
                public void onBodyErrorIsNull() {

                }

                @Override
                public void onFailure(Throwable t) {

                }
            }, ConfigService.PATIENT + ConfigService.HISTORY_EVALUATION + 400);
        }

        private void resetViewport() {
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = minScore - 10;
            v.top = 30;
            v.left = 0;
            v.right = Float.parseFloat(historyEvaluations.get(historyEvaluations.size() - 1).getEvaluationTest().getFrequencyPatient());
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
            chart.setCurrentViewportWithAnimation(v);
            chart.startDataAnimation();
        }

        private void generateData() {
            List<Line> lines = new ArrayList<>();
            List<PointValue> values = new ArrayList<>();

            for (int i = 0; i < historyEvaluations.size(); i++) {
                EvaluationTest evaluationTest = historyEvaluations.get(i).getEvaluationTest();
                String date = DateTHFormat.getInstance().getMonth(new Date(evaluationTest.getTestDate()));
                values.add(new PointValue(Float.parseFloat(evaluationTest.getFrequencyPatient()),
                        Float.parseFloat(evaluationTest.getResultScore()))
                        .setLabel(date + " " + Integer.parseInt(evaluationTest.getResultScore()) + " คะแนน"));
                if (minScore > Float.parseFloat(evaluationTest.getResultScore())) {
                    minScore = Float.parseFloat(evaluationTest.getResultScore());
                }
            }
            Line line = new Line(values);
            line.setColor(getResources().getColor(R.color.colorBackgroundDark));
            line.setPointColor(getResources().getColor(R.color.colorComplete));

            line.setFilled(true);
            line.setHasLabels(true);
            line.setHasLabelsOnlyForSelected(true);

            lines.add(line);
            data = new LineChartData(lines);

            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            axisY.setName("คะแนนแบบทดสอบ");
            axisX.setName("จำนวนครั้งที่เข้าใช้งาน");
            axisX.setTextColor(getResources().getColor(R.color.colorAllblack));
            axisY.setTextColor(getResources().getColor(R.color.colorAllblack));

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
            chart.setValueSelectionEnabled(true);
            chart.setLineChartData(data);
            chart.setZoomType(ZoomType.HORIZONTAL);
            resetViewport();
        }

        private class ValueTouchListener implements LineChartOnValueSelectListener {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
                evaluationTestSingles = new ArrayList<>();
                for (String evaluationCategory : EVALUATION_CATEGORY) {
                    evaluationTestSingles.add(HistoryEvaluationModel.getInstance()
                            .getEvaluationTestByCategory(evaluationCategory, historyEvaluations.get(pointIndex)
                                    .getEvaluationTest()));
                }
                Log.d("onValueSelected", "onValueSelected: "+evaluationTestSingles);
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }
    }
}