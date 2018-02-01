package com.example.sreekanthkasani.login;

/**
 * Created by sreekanth kasani  ') on 11/30/2017.
 */

public class user_info {
    private String username,email,password,user_id;

    public user_info(String username, String email, String password, String user_id) {
        this.email = email;
        this.password = password;
        this.user_id = user_id;
        this.username = username;
    }

    public user_info() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "user_info{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
