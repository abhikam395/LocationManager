package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.locationmanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtRegister;
    private Button btnFb, btnGmail, btnContinue;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        txtRegister  = findViewById(R.id.login_text_register);
        btnFb = findViewById(R.id.login_btn_fb);
        btnGmail = findViewById(R.id.login_btn_g_mail);
        btnContinue = findViewById(R.id.login_btn_continue);

        edtEmail = findViewById(R.id.login_edt_email);
        edtPassword = findViewById(R.id.login_edt_password);

        setListener();
    }

    private void setListener(){
        txtRegister.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    private void setInputs(){
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
    }

    private boolean isValidate(){
        setInputs();

        String email = this.email.trim(), password = this.password.trim();

        if (email == null || !email.contains("@gmail.com"))
            return  false;
        else if(password == null || password.length() < 5)
            return  false;
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_text_register: {
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            }
            case R.id.login_btn_continue: {
                if(isValidate()){
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                else
                    Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}