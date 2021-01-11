package com.example.locationmanager.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationmanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtLogin;
    private Button btnFb, btnGmail, btnContinue;
    private TextInputEditText edtUsername;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init(){
        txtLogin  = findViewById(R.id.register_text_login);
        btnFb = findViewById(R.id.register_btn_fb);
        btnGmail = findViewById(R.id.register_btn_g_mail);
        btnContinue = findViewById(R.id.register_btn_continue);

        edtUsername = findViewById(R.id.register_edt_username);
        edtEmail = findViewById(R.id.register_edt_email);
        edtPassword = findViewById(R.id.register_edt_password);

        setListener();
    }

    private void setListener(){
        txtLogin.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    private void setInputs(){
        username = edtUsername.getText().toString();
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
    }

    private boolean isValidate(){
        setInputs();

        String name = this.username.trim(), email = this.email.trim(), password = this.password.trim();

        if(name == null || name.length() < 5)
            return false;
        else if (email == null || !email.contains("@gmail.com"))
            return  false;
        else if(password == null || password.length() < 5)
            return  false;
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_text_login: {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
            case R.id.register_btn_continue: {
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