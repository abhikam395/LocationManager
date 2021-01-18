package com.example.locationmanager.utils;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class GlobalApplication extends Application {

    private static final String TAG = "GlobalApplication";
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;
    private static SharePreferenceManager sharePreferenceManager;
    private static Timer timer = null;


    @Override
    public void onCreate() {
        super.onCreate();
        setUp();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        sendPingToServer();
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
        stopPing();
    }

    private static boolean activityVisible;
    private void setUp(){
        sharePreferenceManager = new SharePreferenceManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("status");
    }


    private static void sendPingToServer(){
        final int MAX_TIME = 10 * 1000;
        class PingTimer extends TimerTask {
            public void run(){
                databaseReference.child(String.valueOf(sharePreferenceManager.getUser().id)).child("last_seen")
                        .setValue(System.currentTimeMillis());
                Log.d(TAG, "run: " );
            }
        }
        timer = new Timer();
        timer.schedule(new PingTimer(), 1000, MAX_TIME);
    }

    private static void stopPing(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
