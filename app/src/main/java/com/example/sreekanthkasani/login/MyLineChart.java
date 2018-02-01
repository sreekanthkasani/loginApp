package com.example.sreekanthkasani.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * Created by sreekanth kasani  ') on 12/4/2017.
 */

public class MyLineChart extends Fragment {

    public static MyLineChart newInstance(int page, String title){
        MyLineChart myLineChart = new MyLineChart();
        Bundle args = new Bundle();
        args.putInt("hello",page);
        myLineChart.setArguments(args);
        return myLineChart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.linechart,container,false);
        LineChart lineChart = (LineChart) view.findViewById(R.id.line_chart);
//
//        lineChart.setOnChartGestureListener(this);
//        lineChart.setOnChartValueSelectedListener(this);
//
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);



        //create a dataset

        ArrayList<Entry> yvalues = new ArrayList<>();

        yvalues.add(new Entry(0,60f));
        yvalues.add(new Entry(1,20f));
        yvalues.add(new Entry(2,40f));
        yvalues.add(new Entry(3,70f));
        yvalues.add(new Entry(4,90f));
        yvalues.add(new Entry(5,10f));
        yvalues.add(new Entry(6,50f));


        ArrayList<Entry> set_values = new ArrayList<>();

        set_values.add(new Entry(0,60f));
        set_values.add(new Entry(4,30f));
        set_values.add(new Entry(6,40f));
        set_values.add(new Entry(8,70f));
        set_values.add(new Entry(1,90f));
        set_values.add(new Entry(3,10f));
        set_values.add(new Entry(5,50f));



        //set the values to the line dataset
        LineDataSet set1 = new LineDataSet(yvalues,"dataset1");
        set1.setFillAlpha(110);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextColor(Color.GREEN);
        set1.setHighlightEnabled(true);


        LineDataSet set2 = new LineDataSet(set_values,"dataset2");
        set2.setFillAlpha(110);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);


        //assign the values of the dataset to the arraylist
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(set1);
        lineDataSets.add(set2);

        LineData lineData = new LineData(lineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();




        return  view;
    }
}






















