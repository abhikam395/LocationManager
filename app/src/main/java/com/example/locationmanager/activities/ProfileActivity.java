package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.locationmanager.R;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.utils.SharePreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    private SharePreferenceManager sharePreferenceManager;
    private TextView lblName;
    private TextView lblEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init(){
        sharePreferenceManager = new SharePreferenceManager(this);

        lblName = findViewById(R.id.lbl_name_profile);
        lblEmail = findViewById(R.id.lbl_email_profile);

        setData();
    }

    private void setData(){
        AuthUser authUser = sharePreferenceManager.getUser();
        lblName.setText(authUser.name);
        lblEmail.setText(authUser.email);
    }
}