package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.locationmanager.R;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.android.gms.maps.GoogleMap;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";
    private SharePreferenceManager sharePreferenceManager;
    private RadioGroup radioGroup;
    private RadioButton radioButtonNormal;
    private RadioButton radioButtonSatellite;
    private RadioButton radioButtonTerrain;
    private RadioButton radioButtonHybrid;
    private RadioButton selectedRadioButton;

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
        radioButtonNormal = findViewById(R.id.radio_btn_normal_setting);
        radioButtonSatellite = findViewById(R.id.radio_btn_satellite_setting);
        radioButtonTerrain = findViewById(R.id.radio_btn_terrain_setting);
        radioButtonHybrid = findViewById(R.id.radio_btn_hybrid_setting);

        int selectedButton = sharePreferenceManager.getMapType() - 1;
        selectedButton = selectedButton >= 0 ? selectedButton : 0;
        selectedRadioButton = (RadioButton)radioGroup.getChildAt(selectedButton);
        selectedRadioButton.setChecked(true);
        setListener();
    }

    private void setListener(){
        radioButtonNormal.setOnClickListener(this);
        radioButtonSatellite.setOnClickListener(this);
        radioButtonTerrain.setOnClickListener(this);
        radioButtonHybrid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radio_btn_normal_setting:
                sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.radio_btn_satellite_setting:
                sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.radio_btn_terrain_setting:
                sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.radio_btn_hybrid_setting:
                sharePreferenceManager.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
    }
}