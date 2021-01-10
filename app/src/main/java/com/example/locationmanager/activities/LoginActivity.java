package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locationmanager.R;

public class LoginActivity extends AppCompatActivity {

    private TextView txtRegister;
    private Button btnFb, btnGmail, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister  = findViewById(R.id.login_text_register);
        btnFb = findViewById(R.id.login_btn_fb);
        btnGmail = findViewById(R.id.login_btn_g_mail);
        btnContinue = findViewById(R.id.login_btn_continue);

        //navigate to RegisterActivity
        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}