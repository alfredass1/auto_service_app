package com.auto.autoservice;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private String username;
    private String password;
    private String email;

    private SharedPreferences sharedPreferences;
    private static final String PREFERENCES_PACKAGE_NAME = "com.auto.carservice";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBER_ME_KEY = "rememberMe";

    // Konstruktorius skirtas naujo vartotojo registracijai
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    // Konstruktorius skirtas vartotojo prisijungimui
    public User(Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_PACKAGE_NAME,
                Context.MODE_PRIVATE);
    }

    public String getUsernameForRegistration() {
        return username;
    }

    public String getPasswordForRegistration() {
        return password;
    }

    public String getEmailForRegistration() {
        return email;
    }

    public void setUsernameForRegistration(String username) {
        this.username = username;
    }

    public void setPasswordForRegistration(String password) {
        this.username = password;
    }

    public void setEmailForRegistration(String email) {
        this.email = email;
    }

    public String getUsernameForLogin(){
        return this.sharedPreferences.getString(USERNAME_KEY,"");

    }
    public void setUsernameForLogin(String username){

        this.sharedPreferences.edit().putString(USERNAME_KEY, username).commit();
    }

    public String getPasswordForLogin(){
        return this.sharedPreferences.getString(PASSWORD_KEY,"");

    }

    public void setPasswordForLogin(String password){

        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).commit();
    }

    public boolean isRememberedForLogin(){
        return this.sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);

    }

    public void setRememberMeKeyForLogin(boolean rememberMeKey){
        this.sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY, rememberMeKey).commit();

    }

}
