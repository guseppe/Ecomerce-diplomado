package com.example.ecomerce_diplomado.utils.usersession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ecomerce_diplomado.data.model.LoggedInUser;
import com.example.ecomerce_diplomado.ui.login.LoginActivity;
import com.google.gson.Gson;

public class UserSession {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "UserSessionPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String USER = "USER";


    private void initSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public UserSession(Context context){
        this.context = context;
        initSharedPreferences();
    }

    public void CreateSession(LoggedInUser user){
        Gson gson = new Gson();
        String userJson = gson.toJson(user,LoggedInUser.class);
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(USER,userJson);
        editor.commit();
    }

    public boolean isLoggedIn(){
        boolean isLoggedInVal = sharedPreferences.getBoolean(IS_LOGIN,false);
        return isLoggedInVal;
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }

    public void checkLogin(){
        if(!isLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public LoggedInUser getLoggedInUser(){
        if(isLoggedIn()){
            Gson gson = new Gson();
            LoggedInUser user = gson.fromJson(sharedPreferences.getString(USER,""),LoggedInUser.class);
            return user;
        }
        return null;
    }

}
