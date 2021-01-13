package com.example.locationmanager.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationmanager.R;
import com.example.locationmanager.models.AuthResponse;
import com.example.locationmanager.models.User;
import com.example.locationmanager.services.AuthInterface;
import com.example.locationmanager.services.RestClient;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    AuthInterface authInterface = RestClient.getClient().create(AuthInterface.class);
                    Call<AuthResponse> call = authInterface.register(user.name, user.email, user.password);
                    call.enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            AuthResponse authResponse = response.body();
                            if(authResponse.status){
                                sharePreferenceManager.setToken(authResponse.data.token);
                                startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(),String.valueOf(authResponse.error.message),
                                        Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {

                        }
                    });
                }
                else
                    Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}