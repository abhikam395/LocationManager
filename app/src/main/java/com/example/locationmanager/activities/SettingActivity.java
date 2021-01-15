package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.locationmanager.R;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.android.gms.maps.GoogleMap;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";
    private RadioGroup radioGroup;
    private SharePreferenceManager sharePreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    private void init(){
        sharePreferenceManager = new SharePreferenceManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);

        radioGroup = findViewById(R.id.radio_ground_setting);

        setListener();
    }

    private void setListener(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: " + checkedId);
                if(checkedId  == 1){
                    sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else if(checkedId == 2){
                    sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
                else if(checkedId == 3){
                    sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else if(checkedId == 4){
                    sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });
    }
}