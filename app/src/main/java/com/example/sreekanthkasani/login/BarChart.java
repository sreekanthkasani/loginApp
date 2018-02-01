package com.example.sreekanthkasani.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sreekanth kasani  ') on 12/4/2017.
 */

public class BarChart extends Fragment {

    public static BarChart newInstance(int page, String title){
        BarChart BarChart = new BarChart();
        Bundle args = new Bundle();
        args.putInt("hello",page);
        BarChart.setArguments(args);
        return  BarChart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro2,container,false);
        return  view;
    }
}
