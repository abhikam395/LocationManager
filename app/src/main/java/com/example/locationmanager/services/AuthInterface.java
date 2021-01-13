package com.example.locationmanager.services;

import com.example.locationmanager.models.AuthResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthInterface {
    @POST("/api/v1/auth/register")
    Call<AuthResponse> register(
        @Query("name") String name,
        @Query("email") String email,
        @Query("password") String password
    );

    @POST("/api/v1/auth/login")
    Call<AuthResponse> login(
         @Query("email") String email,
         @Query("password") String password
    );
}
