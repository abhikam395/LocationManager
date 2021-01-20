package com.example.locationmanager.models.sources;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.locationmanager.models.AuthResponse;
import com.example.locationmanager.services.AuthService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthRemoteDataSource {

    private static final String TAG = "AuthRemoteDataSource";
    private final AuthService authService;

    @Inject
    public AuthRemoteDataSource(Retrofit retrofit) {
        authService = retrofit.create(AuthService.class);
    }

    /**
     * register user
     * @param name
     * @param email
     * @param password
     * @return
     */
    public MutableLiveData<AuthResponse> requestUserRegister(String name, String email, String password){
        final MutableLiveData<AuthResponse> mutableLiveData = new MutableLiveData<>();
        authService.register(name, email, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                mutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
        return mutableLiveData;
    }

    /**
     * login user
     * @param email
     * @param password
     * @return
     */
    public MutableLiveData<AuthResponse> requestUserLogin(String email, String password){
        final MutableLiveData<AuthResponse> mutableLiveData = new MutableLiveData<>();
        authService.login(email, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                mutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
        return mutableLiveData;
    }
}
