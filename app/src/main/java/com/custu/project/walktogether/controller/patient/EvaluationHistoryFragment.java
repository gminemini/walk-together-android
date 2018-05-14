package com.custu.project.walktogether.controller.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.custu.project.walktogether.data.Evaluation.EvaluationCategory.EVALUATION_CATEGORY;

public class EvaluationHistoryFragment extends Fragment {

    private LineChartView chart;
    private LineChartData data;

    private ArrayList<HistoryEvaluation> historyEvaluations = new ArrayList<>();
    private float minScore = Float.MAX_VALUE;
    private Long idPatient;
    private int right;

    public EvaluationHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

        chart = rootView.findViewById(R.id.chart);
        chart.setViewportCalculationEnabled(false);
        if (getArguments() != null) {
            idPatient = getArguments().getLong("idPatient");
        }
        getData();
        return rootView;
    }

    private void getData() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    historyEvaluations = HistoryEvaluationModel.getInstance().getHistoryEvaluations(object);
                    if (historyEvaluations.size() > 0) {
                        generateData();
                    }
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
        }, ConfigService.PATIENT + ConfigService.HISTORY_EVALUATION + idPatient);
    }

    private void resetViewport() {
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 30;
        v.left = 0;
        if (right < 6) {
            right = 6;
            v.right = right;
        } else
            v.right = right;

        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
        chart.setCurrentViewportWithAnimation(v);
        chart.startDataAnimation();
    }

    private void generateData() {
        right = historyEvaluations.size();
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();

        //Evaluation
        for (int i = 0; i < historyEvaluations.size(); i++) {
            EvaluationTest evaluationTest = historyEvaluations.get(i).getEvaluationTest();
            String date = DateTHFormat.getInstance().getMonth(new Date(evaluationTest.getTestDate()));
            values.add(new PointValue(i, Float.parseFloat(evaluationTest.getResultScore()))
                    .setLabel(date + " " + Integer.parseInt(evaluationTest.getResultScore()) + " คะแนน"));
            if (minScore > Float.parseFloat(evaluationTest.getResultScore())) {
                minScore = Float.parseFloat(evaluationTest.getResultScore());
            }
        }

        Line line = new Line(values);
        line.setColor(getResources().getColor(R.color.colorBackgroundDark));
        line.setPointColor(getResources().getColor(R.color.colorComplete));
        line.setPointRadius(8);

        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);

        lines.add(line);

        //Mission
        values = new ArrayList<>();
        for (int i = 0; i < historyEvaluations.size(); ++i) {
            if (i == 0) {
                EvaluationTest evaluationTest = historyEvaluations.get(i).getEvaluationTest();
                values.add(new PointValue(i, Float.parseFloat(evaluationTest.getFrequencyPatient())));
            } else {
                EvaluationTest evaluationTest = historyEvaluations.get(i).getEvaluationTest();
                EvaluationTest evaluationTest2 = historyEvaluations.get(i - 1).getEvaluationTest();
                Float frequency = Float.parseFloat(evaluationTest.getFrequencyPatient());
                Float frequency2 = Float.parseFloat(evaluationTest2.getFrequencyPatient());
                String date = DateTHFormat.getInstance().getMonth(new Date(evaluationTest.getTestDate()));
                values.add(new PointValue(i, frequency - frequency2)
                        .setLabel(date + " " +(int)(frequency - frequency2) + " ครั้ง"));
            }
        }

        line = new Line(values);
        line.setColor(getResources().getColor(R.color.colorPath));
        line.setPointColor(getResources().getColor(R.color.colorPath));
        line.setPointRadius(8);
        lines.add(line);

        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);


        data = new LineChartData(lines);
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < right; i++) {
            if (i < historyEvaluations.size()) {
                EvaluationTest evaluationTest = historyEvaluations.get(i).getEvaluationTest();
                String date = DateTHFormat.getInstance().getMonth(new Date(evaluationTest.getTestDate()));
                axisValues.add(new AxisValue(i).setLabel(date));
            } else {
                axisValues.add(new AxisValue(i).setLabel(""));
            }
        }

        Axis axisX = new Axis(axisValues);
        axisX.setName("ครั้งที่ทำแบบทดสอบ");
        axisX.setMaxLabelChars(4);
        axisX.setHasTiltedLabels(true);
        data.setAxisXBottom(axisX);

        Axis axisYLeft = new Axis().setHasLines(true);
        axisYLeft.setName("คะแนนแบบทดสอบ");
        axisYLeft.setLineColor(getResources().getColor(R.color.colorBackground));

        Axis axisYRight = new Axis().setHasLines(true);
        axisYRight.setName("จำนวนที่เข้าเล่นภารกิจ");
        axisYRight.setLineColor(getResources().getColor(R.color.colorPath));


        axisX.setTextColor(getResources().getColor(R.color.colorAllblack));
        axisYLeft.setTextColor(getResources().getColor(R.color.colorBackground));
        axisYRight.setTextColor(getResources().getColor(R.color.colorPath));

        data.setAxisXBottom(axisX);

        data.setAxisYLeft(axisYLeft);
        data.setAxisYRight(axisYRight);

        chart.setValueSelectionEnabled(true);
        chart.setLineChartData(data);
        chart.setZoomType(ZoomType.HORIZONTAL);
        resetViewport();
    }
}
