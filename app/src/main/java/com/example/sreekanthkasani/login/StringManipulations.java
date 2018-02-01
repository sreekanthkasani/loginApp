package com.example.sreekanthkasani.login;

/**
 * Created by sreekanth kasani  ') on 11/30/2017.
 */

public class StringManipulations {

    public static String encodeUsername(String firstname,String lastname){
        String username = firstname+"_"+lastname;
        return username;
    }

    public static String decodeUsername(String username){
        String fname = username.split("_")[0];
        String lname = username.split("_")[1];
        return (fname+" "+lname);
    }
}
