package com.example.sreekanthkasani.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sreekanthkasani.login.login.OLoginActivity;
import com.example.sreekanthkasani.login.medReminder.callsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST = 1;
    private String TAG = "HomeActivity";
    private TextView textView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkReqPermissions();
        setUpFirebaseAuth();
        setUpViewPager();
    }


    /***
     * returns current tab number
     * @return
     */

    public int getCurrrentTabNumber(){
        return viewPager.getCurrentItem();
    }




    /**
     * request for permission check
     */
    private void checkReqPermissions() {
        if(checkPermissionArray(AndroidUtils.permissions)){

        }else{
            verifyPermissions(AndroidUtils.permissions);
        }
    }


    //-------------------------------Check Permissions------------------------------------//

    /**
     * checks wether required set of permissions[] are granted or not
     * @param permissions
     * @return
     */
    public boolean checkPermissionArray(String[] permissions){
        for(int i=0;i<permissions.length;i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    /**
     * checks wether the single permission is granted or not
     * @param check
     * @return
     */
    public boolean checkPermissions(String check){
        int permissionRequest = ContextCompat.checkSelfPermission(HomeActivity.this, check);
        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }

    /**
     * this method requests from the user
     * @param permissions
     */
    public void verifyPermissions(String[] permissions){
        ActivityCompat.requestPermissions(
                HomeActivity.this,
                permissions,
                MY_PERMISSIONS_REQUEST);

    }

    //--------------------------------------------------------------------------------------------//



    public void setUpViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.setFragments(new GalleryFragment());
        adapter.setFragments(new chatsFragment());
        adapter.setFragments(new ReminderFragment());
        adapter.setFragments(new callsFragment());

        viewPager = (ViewPager) findViewById(R.id.vpTabSlider);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f;
        layout.setLayoutParams(layoutParams);

        viewPager.setCurrentItem(2);
        tabLayout.getTabAt(0).setIcon(R.drawable.camera);
        tabLayout.getTabAt(1).setText("chats");
        tabLayout.getTabAt(2).setText("status");
        tabLayout.getTabAt(3).setText("calls");

    }

    /***
     *
     * --------------Setting up firebase------------------------------------
     * ------------- Authenticating the user (FIrebase)---------------------
     *
     ***/

    private void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    textView.setText("user"+user.getEmail());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(getApplicationContext(),OLoginActivity.class));
                }

            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
