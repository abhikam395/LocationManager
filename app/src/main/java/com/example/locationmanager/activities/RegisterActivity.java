package com.example.locationmanager.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationmanager.R;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.User;
import com.example.locationmanager.models.UserResponse;
import com.example.locationmanager.utils.GlobalApplication;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.example.locationmanager.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private TextView txtLogin;
    private Button btnFb, btnGmail, btnContinue;
    private TextInputEditText edtUsername;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private String username, email, password;
    private SharePreferenceManager sharePreferenceManager;
    private User user;

    @Inject
    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((GlobalApplication)getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        sharePreferenceManager = new SharePreferenceManager(this);
        String token = sharePreferenceManager.getToken() != null ? sharePreferenceManager.getToken() : null;
        if(token != null) {
            startActivity(new Intent(this, PermissionActivity.class));
            finish();
        }

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
        user = new User(name, email, password);

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
                    authViewModel.registerUser(user.name, user.email, user.password).observe(this, authResponse -> {
                        if(authResponse.status){
                            UserResponse userResponse = authResponse.data.getUser();
                            sharePreferenceManager.setToken(authResponse.data.token);
                            sharePreferenceManager.setUser(new AuthUser(userResponse.getId(),
                                    userResponse.getName(), userResponse.getEmail()));
                            startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),String.valueOf(authResponse.error.message),
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