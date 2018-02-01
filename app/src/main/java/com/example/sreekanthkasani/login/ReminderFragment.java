package com.example.sreekanthkasani.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class ReminderFragment extends Fragment {


    private TextView temp;
    private String course;
    private int[] graphs = {
            R.layout.intro1,
            R.layout.intro2,
            R.layout.intro3,
            R.layout.intro4,
            R.layout.intro5
    };


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reminder, container, false);
        TextView stat_header = (TextView) view.findViewById(R.id.stat_header);
        Spinner spinner = (Spinner) view.findViewById(R.id.stat_sortby);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.graph_slider);
        viewPager.setAdapter(new GraphViewPagerAdapter(getActivity().getSupportFragmentManager()));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getActivity().getApplicationContext(), R.array.pin_codes, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.stat_sortby:
                        temp = (TextView) view;
                        if(temp == null){
                            course = "week";
                        }else {
                            AndroidUtils.print(getActivity(), (String) temp.getText());
                            course = (String) temp.getText();
                        }
                        break;

                    default:
                        course="week";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                course = "week";
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return  view;
    }
}
