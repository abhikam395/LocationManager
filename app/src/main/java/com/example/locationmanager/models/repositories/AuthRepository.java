package com.example.locationmanager.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.locationmanager.models.sources.AuthRemoteDataSource;
import com.example.locationmanager.models.AuthResponse;

import javax.inject.Inject;

public class AuthRepository {

    private final AuthRemoteDataSource authRemoteDataSource;
    private MutableLiveData mutableLiveData;

    @Inject
    public AuthRepository(AuthRemoteDataSource authRemoteDataSource) {
        this.authRemoteDataSource = authRemoteDataSource;
    }

    public MutableLiveData<AuthResponse> requestRegister(String name, String email, String password){
        if(mutableLiveData == null){
            mutableLiveData = authRemoteDataSource.requestUserRegister(name, email, password);
        }
        return mutableLiveData;
    }

    public MutableLiveData<AuthResponse> requestLogin(String email, String password){
        if(mutableLiveData == null){
            mutableLiveData = authRemoteDataSource.requestUserLogin(email, password);
        }
        return mutableLiveData;
    }
}
