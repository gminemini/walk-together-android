package com.custu.project.walktogether;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.custu.project.project.walktogether.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class GraphActivity extends AppCompatActivity {

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
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

            chart = rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            generateData();
            chart.setViewportCalculationEnabled(false);
            resetViewport();
            chart.startDataAnimation();
            return rootView;
        }

        private void resetViewport() {
            // Reset viewport height range to (0,100)
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 30;
            v.left = 0;
            v.right = 15;
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
            chart.setCurrentViewportWithAnimation(v);
        }

        private void generateData() {
            List<Line> lines = new ArrayList<>();
            List<PointValue> values = new ArrayList<>();
            values.add(new PointValue(2, 20).setLabel("มกราคม ได้ "+20+"คะแนน"));
            values.add(new PointValue(4, 23).setLabel("เมษายน ได้ "+23+"คะแนน"));
            values.add(new PointValue(6, 22).setLabel("มิถุนายน ได้ "+22+"คะแนน"));
            values.add(new PointValue(9, 19).setLabel("สิงหาคม ได้ "+19+"คะแนน"));
            values.add(new PointValue(13, 26).setLabel("ธันวาคม ได้ "+26+"คะแนน"));

            Line line = new Line(values);
            line.setColor(getResources().getColor(R.color.colorBackgroundDark));
            line.setPointColor(getResources().getColor(R.color.colorComplete));

            line.setCubic(true);
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
        }

        private class ValueTouchListener implements LineChartOnValueSelectListener {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }
    }
}