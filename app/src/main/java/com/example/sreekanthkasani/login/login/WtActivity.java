package com.example.sreekanthkasani.login.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sreekanthkasani.login.R;

public class WtActivity extends AppCompatActivity {

    private WalkThrough walkThrough;
    private ViewPager viewPager;
    private WtViewPagerAdapter wtViewPagerAdapter;
    private TextView[] dots;
    private Button skip;
    private ImageView next;
    private LinearLayout dotsLayout;
    private int[] layouts = {
            R.layout.intro1,
            R.layout.intro2,
            R.layout.intro3,
            R.layout.intro4,
            R.layout.intro5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walkThrough = new WalkThrough(this);

        if((walkThrough.isNewInstalled()).equals("known")){
            if((walkThrough.getUserStatus()).equals("new")){
                walkThrough.setUserStatus("registered");
                Intent i = new Intent(WtActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }else {
                Intent i = new Intent(WtActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        }else{
            walkThrough.setInstallationStatus("known");
        }

        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.walkthrough);

        viewPager = (ViewPager) findViewById(R.id.wtpager);
        dotsLayout = (LinearLayout) findViewById(R.id.indicators);
        next = (ImageView)findViewById(R.id.btn_nxt);
        skip = (Button) findViewById(R.id.btn_skip);
        wtViewPagerAdapter = new WtViewPagerAdapter(WtActivity.this,layouts);
        viewPager.setAdapter(wtViewPagerAdapter);

        ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == 4){
                    next.setImageResource(R.drawable.login);
                    skip.setVisibility(View.VISIBLE);
                }else{
                    next.setImageResource(R.drawable.nxt_btn);
                    skip.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(viewListener);
        viewPager.setOffscreenPageLimit (5);
        addBottomDots(0);
        changeStatusBarColor();

        skip.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(WtActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if(current<5){
                    viewPager.setCurrentItem(current);
                }else{
                    Intent i = new Intent(WtActivity.this,RegisterActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private int getItem(int i){
        return viewPager.getCurrentItem()+1;
    }



    private void addBottomDots(int position){
          dots = new TextView[5];
          int[] Active = getResources().getIntArray(R.array.iActiveFinal);
          int[] inActive = getResources().getIntArray(R.array.inActiveFinal);
          dotsLayout.removeAllViews();
          for(int i=0;i<5;i++){
              dots[i] = new TextView(this);
              dots[i].setText(Html.fromHtml("&#8226;"));
              dots[i].setTextSize(35);
              dots[i].setTextColor(getResources().getColor(R.color.inActive));
              dotsLayout.addView(dots[i]);
          }

          if (dots.length > 0){
              dots[position].setTextColor(getResources().getColor(R.color.iActive));
          }

    }


    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
