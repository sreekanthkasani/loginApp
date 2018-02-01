package com.example.sreekanthkasani.login.login;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sreekanthkasani.login.AndroidUtils;
import com.example.sreekanthkasani.login.HomeActivity;
import com.example.sreekanthkasani.login.R;
import com.example.sreekanthkasani.login.testActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class OLoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private String uname,pswd;
    private Boolean flag;
    private TextView registerUser;
    private String TAG = "LoginActivity";
    private View progressOverlay;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ologin);

        setUpFirebaseAuth();

        progressOverlay = findViewById(R.id.progress_overlay);
        username = (EditText) findViewById(R.id.lusername);
        password = (EditText) findViewById(R.id.lpassword);
        login = (Button) findViewById(R.id.llogin);
        registerUser = (TextView) findViewById(R.id.go_signup);

        registerUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Intent register = new Intent(OLoginActivity.this,RegisterActivity.class);
                    startActivity(register);
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    return true;
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
                boolean isDataValid = validate();
                if (isDataValid) {
                    AuthenticateUserLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "unable to signup", Toast.LENGTH_SHORT).show();
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0.4f, 200);
                }
            }
        });
    }


    public boolean validate() {
        fetchData();
        flag = true;
        if (uname.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(uname).matches()) {
            username.setError("invalid email");
            flag = false;
        }
        if (pswd.isEmpty() || pswd.length() > 32) {
            password.setError("password required");
            flag = false;

        }
        return flag;
    }

    public void fetchData(){
        uname = username.getText().toString().trim();
        pswd = password.getText().toString().trim();
    }


    private void AuthenticateUserLogin(){
        mAuth.signInWithEmailAndPassword(uname,pswd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(task.isSuccessful()){
                    try {
                        if (user.isEmailVerified()){
                            startActivity(new Intent(getApplicationContext(),testActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "please verify email", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            SystemClock.sleep(1000*5);
                            finish();
                            startActivity(getIntent());
                        }
                    }catch (NullPointerException ne){
                        ne.printStackTrace();
                    }
                    AndroidUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);;
                }else {
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0.4f, 200);
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(getApplicationContext(), "invali username or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    /***
     *
     * -----------------Setting up firebase------------------------------------
     * ------------- Authenticating the user (FIrebase)---------------------
     ***/


    private void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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
