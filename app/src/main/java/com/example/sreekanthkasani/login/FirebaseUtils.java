package com.example.sreekanthkasani.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sreekanth kasani  ') on 11/30/2017.
 */

public class FirebaseUtils  {

    private DatabaseReference loginDb;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mcontext;
    private String userId;
    private Boolean flag=false;

    public FirebaseUtils(Context context){
         this.mcontext = context;
         mfirebaseDatabase = FirebaseDatabase.getInstance();
         loginDb = mfirebaseDatabase.getReference();
         mAuth = FirebaseAuth.getInstance();
         if(mAuth.getCurrentUser() !=null){
             userId = mAuth.getCurrentUser().getUid();
         }
        Toast.makeText(mcontext,"userId"+ userId,Toast.LENGTH_LONG).show();

    }


    public boolean checkUserNameCollision(String username, DataSnapshot dataSnapshot){
        Log.d("FirebaseUtils","Checking if there is collision of username"+ username);

        user_info users = new user_info();

        for(DataSnapshot ds:dataSnapshot.child("users").getChildren()){
            Log.d("FirebaseUtils","maping" + username +"with"+ ds);
            users.setUsername(ds.getValue(user_info.class).getUsername());
            if(username.equals(users.getUsername())){
                return true;
            }
        }
        return false;
    }

    public boolean sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        flag=true;
                        Toast.makeText(mcontext,"verification code sent to registerd email",Toast.LENGTH_LONG).show();
                    }else{
                        flag=false;
                        Toast.makeText(mcontext,"unable to send email verification",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return flag;
    }



    public boolean addUserToTheDB(String userId,String username,String fname,String lname,String email,String password,String mobile,String pin,String gender,String Dtype ){

        this.userId =userId;
        user_info users=new user_info(username,email,password,userId);
        Toast.makeText(mcontext,"userId"+ userId,Toast.LENGTH_LONG).show();
        Log.wtf("userId",userId);
        loginDb.child("users").child(userId).setValue(users);
        return true;
    }























}
