package com.example.locationmanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationmanager.models.AuthResponse;
import com.example.locationmanager.models.AuthUserInfo;
import com.example.locationmanager.models.repositories.AuthRepository;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private MutableLiveData<AuthResponse> registerMutableLiveData;
    private MutableLiveData<AuthResponse> loginMutableLiveData;
    private final AuthRepository authRepository;

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<AuthResponse> registerUser(String name, String email, String password){
        if(registerMutableLiveData == null){
            registerMutableLiveData = authRepository.requestRegister(name, email, password);
        }
        return registerMutableLiveData;
    }

    public LiveData<AuthResponse> loginUser(String email, String password){
        if(loginMutableLiveData == null){
            loginMutableLiveData = authRepository.requestLogin(email, password);
        }
        return loginMutableLiveData;
    }
}
