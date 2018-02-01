package com.example.sreekanthkasani.login.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sreekanthkasani.login.AndroidUtils;
import com.example.sreekanthkasani.login.FirebaseUtils;
import com.example.sreekanthkasani.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView profile;
    private EditText firstname, lastname, email, password, contact;
    private String fname, lname, mail, pswd, mobile, isverified = "false";
    private String gender_value, country_pin, diabetes_type,userId;
    private TextView temp, signin, PF, PL;
    private Boolean flag;
    private Spinner pin, gender, dtype;
    private Button register;
    private View progressOverlay;
    private String TAG = "RegisterActivity";
    private FirebaseUtils firebaseUtils;

    //Database declaration
    private DatabaseReference loginDb;
    private FirebaseDatabase mfirebaseDatabase;

    //Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        firebaseUtils = new FirebaseUtils(RegisterActivity.this);
        setUpFirebaseAuth();
        initializeWidgets();

    }


    public void  initializeWidgets(){
        progressOverlay = findViewById(R.id.progress_overlay);
        PF = (TextView) findViewById(R.id.pf);
        PL = (TextView) findViewById(R.id.pl);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pswd);
        contact = (EditText) findViewById(R.id.contact);
        signin = (TextView) findViewById(R.id.signin);

        pin = (Spinner) findViewById(R.id.country_code);
        gender = (Spinner) findViewById(R.id.gender);
        dtype = (Spinner) findViewById(R.id.Dtype);

        ArrayAdapter pinadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pin_codes, android.R.layout.simple_spinner_item);
        pin.setAdapter(pinadapter);
        pin.setOnItemSelectedListener(this);

        ArrayAdapter genderadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gender, android.R.layout.simple_spinner_item);
        gender.setAdapter(genderadapter);
        gender.setOnItemSelectedListener(this);

        ArrayAdapter dtypeadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.diabeties_type, android.R.layout.simple_spinner_item);
        dtype.setAdapter(dtypeadapter);
        dtype.setOnItemSelectedListener(this);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = validate();
                if (isValid) {
                    AndroidUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
                    registerUser();
                } else {
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    Toast.makeText(getApplicationContext(), "unable to signup", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent login = new Intent(RegisterActivity.this, OLoginActivity.class);
                    startActivity(login);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    return true;
                }
                return false;
            }
        });
    }


    public boolean validate(){
        fetchData();
        flag = true;
        if(fname.isEmpty()|| fname.length()>32){
            firstname.setError("enter valid username");
            flag=false;
        }
        if(lname.isEmpty()|| lname.length()>32){
            lastname.setError("enter valid username");
            flag=false;
        }
        if(mail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("enter valid mail");
            flag=false;
        }
        if(pswd.isEmpty()|| pswd.length() > 32){
            password.setError("invalid password selection");
            flag=false;
        }
        if(mobile.isEmpty()|| mobile.length()!=10){
            contact.setError("invalid contact");
            flag=false;
        }
        if(country_pin.isEmpty()){
            firstname.setError("please select code");
            flag=false;
        }
        if(gender_value.isEmpty()){
            firstname.setError("please select gender");
            flag=false;
        }
        if(diabetes_type.isEmpty()){
            firstname.setError("please select diabetes type");
            flag=false;
        }

        return  flag;
    }


    public void fetchData(){
        fname = firstname.getText().toString().trim();
        lname = lastname.getText().toString().trim();
        mail = email.getText().toString().trim();
        pswd = password.getText().toString().trim();
        mobile = contact.getText().toString().trim();

    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(mail,pswd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userId = mAuth.getCurrentUser().getUid();
                    firebaseUtils.sendVerificationEmail();
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    Toast.makeText(getApplicationContext(), "Registered successfully\n please Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), OLoginActivity.class));
                }else{
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"user already exists \n try signing in",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),OLoginActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.country_code:
                temp = (TextView) view;
                country_pin = (String) temp.getText();
                break;
            case R.id.gender:
                temp = (TextView) view;
                gender_value = (String) temp.getText();
                break;
            case R.id.Dtype:
                temp = (TextView) view;
                diabetes_type = (String) temp.getText();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /***
     *
     * -----------------Setting up firebase------------------------------------
     * ------------- Authenticating the user (FIrebase)---------------------
     ***/


    private void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        loginDb = mfirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    loginDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //checking if the username already exists
                            String username = fname+"_"+lname;
                            if(firebaseUtils.checkUserNameCollision(username,dataSnapshot)){
                                username = username + loginDb.push().getKey().substring(5,8);
                                Log.d(TAG,"appending string to the username for storing in the database");
                            }

                            //Adding the user_info to the database
                             userId =mAuth.getCurrentUser().getUid();
                             firebaseUtils.addUserToTheDB(userId,username,fname,lname,mail,pswd,mobile,country_pin,gender_value,diabetes_type);


                            //Adding the private info to the database

                            mAuth.signOut();

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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
