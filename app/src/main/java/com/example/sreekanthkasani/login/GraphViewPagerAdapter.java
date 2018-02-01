package com.example.sreekanthkasani.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by sreekanth kasani  ') on 12/5/2017.
 */

public class GraphViewPagerAdapter extends FragmentStatePagerAdapter{

    private Context context;

    public GraphViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return MyLineChart.newInstance(0,"zero");
            case 1:
                return BarChart.newInstance(1,"one");
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }
}
