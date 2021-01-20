package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationmanager.R;
import com.example.locationmanager.models.AuthResponse;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.User;
import com.example.locationmanager.models.UserResponse;
import com.example.locationmanager.utils.GlobalApplication;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.example.locationmanager.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private TextView txtRegister;
    private Button btnFb, btnGmail, btnContinue;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private String email, password;
    private User user;

    private SharePreferenceManager sharePreferenceManager;

    @Inject
    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((GlobalApplication)getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        sharePreferenceManager = new SharePreferenceManager(this);
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
        user = new User(null, email, password);

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
                    authViewModel.loginUser(user.email, user.password).observe(this, authResponse -> {
                        AuthResponse response = authResponse;
                        if(response.getStatus()){
                            UserResponse userResponse = response.data.getUser();
                            sharePreferenceManager.setToken(response.data.token);
                            sharePreferenceManager.setUser(new AuthUser(userResponse.getId(),
                                    userResponse.getName(), userResponse.getEmail()));
                            startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),String.valueOf(response.getError().getMessage()),
                                    Toast.LENGTH_SHORT).show();
                       });
                }
                else
                    Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}