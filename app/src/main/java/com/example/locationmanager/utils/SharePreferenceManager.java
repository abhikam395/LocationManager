package com.example.locationmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.User;
import com.google.android.gms.maps.model.LatLng;

public class SharePreferenceManager {

    private static final String TAG = "SharePreferenceManager";
    private final String PREFERENCE_NAME = "LOCATION_MANAGER";
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    public SharePreferenceManager(Context context){
        sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void setLastLocation(LatLng latLng){
        editor.putString("lat", String.valueOf(latLng.latitude));
        editor.putString("long", String.valueOf(latLng.longitude));
        editor.apply();
    }

    public void setMapType(int mapType){
        editor.putInt("mapType", mapType);
        editor.apply();
    }

    public void setUser(AuthUser user){
        editor.putInt("id", user.id);
        editor.putString("name", user.name);
        editor.putString("email", user.email);
        editor.apply();
    }

    public void setToken(String token){
        editor.putString("token", token);
        Log.d(TAG, "setToken: " + sharedPreference.getString("token", null));
        editor.apply();
    }

    public AuthUser getUser(){
        int id = sharedPreference.getInt("id", 0);
        String name = sharedPreference.getString("name", null);
        String email = sharedPreference.getString("email", null);
        AuthUser user = new AuthUser(id, name, email);
        return user;
    }

    public LatLng getLastLocation(){
        String latitude = sharedPreference.getString("lat", null);
        String longitude = sharedPreference.getString("long", null);
        if( latitude == null && longitude == null)
            return null;
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    public int getMapType(){
        return sharedPreference.getInt("mapType", 0);
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
