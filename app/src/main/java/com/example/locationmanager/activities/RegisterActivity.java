package com.example.locationmanager.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.locationmanager.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtLogin;
    private Button btnFb, btnGmail, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtLogin  = findViewById(R.id.register_text_login);
        btnFb = findViewById(R.id.register_btn_fb);
        btnGmail = findViewById(R.id.register_btn_g_mail);
        btnContinue = findViewById(R.id.register_btn_continue);

        //navigate to LoginActivity
        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}