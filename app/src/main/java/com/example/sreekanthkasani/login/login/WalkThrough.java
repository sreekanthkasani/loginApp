package com.example.sreekanthkasani.login.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by sreekanth kasani  ') on 11/25/2017.
 */

public class WalkThrough  {
    SharedPreferences  init,user;
    SharedPreferences.Editor initeditor,usereditor;
    Context context;

    public WalkThrough(Context context){
        this.context = context;
        init = context.getSharedPreferences("init",0);
        user = context.getSharedPreferences("user",0);
        initeditor = init.edit();
        usereditor = user.edit();
    }

    public void setInstallationStatus(String b){
        initeditor.putString("init",b);
        initeditor.commit();
    }


    public String isNewInstalled(){
        return init.getString("init","new");
    }

    public void setUserStatus(String k){
        //registered
        usereditor.putString("user",k);
        usereditor.commit();
    }

    public String getUserStatus(){
        return user.getString("user","new");
    }

}
