package com.example.sreekanthkasani.login.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sreekanthkasani.login.HomeActivity;
import com.example.sreekanthkasani.login.R;
import com.example.sreekanthkasani.login.testActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        imageView = (ImageView) findViewById(R.id.logo);

        Animation fadeIn = AnimationUtils.loadAnimation(this,R.anim.fadein);
        imageView.startAnimation(fadeIn);

        final Intent i = new Intent(this,testActivity.class);

        Thread thread =  new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
