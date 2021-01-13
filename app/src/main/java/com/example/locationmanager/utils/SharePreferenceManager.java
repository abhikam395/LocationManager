package com.example.locationmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.locationmanager.models.User;

public class SharePreferenceManager {

    private static final String TAG = "SharePreferenceManager";
    private final String PREFERENCE_NAME = "LOCATION_MANAGER";
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    public SharePreferenceManager(Context context){
        sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void setUser(User user){
//        editor.putInt("id", user.id);
        editor.putString("name", user.name);
        editor.putString("email", user.email);
        editor.apply();
    }

    public void setToken(String token){
        editor.putString("token", token);
        Log.d(TAG, "setToken: " + sharedPreference.getString("token", null));
        editor.apply();
    }

    public User getUser(){
        int id = sharedPreference.getInt("id", 0);
        String name = sharedPreference.getString("name", null);
        String email = sharedPreference.getString("email", null);
        User user = new User(name, email, "sdfsdf");
        return user;
    }

    public String getToken(){
        return sharedPreference.getString("token", null);
    }

    public void clear(){
        editor.remove("id");
        editor.remove("name");
        editor.remove("email");
        editor.remove("token");
        editor.apply();
    }
}
