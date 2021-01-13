package com.example.locationmanager.services;

import com.example.locationmanager.models.AuthResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationInterface {
    @GET("/api/v1/location")
    Call<AuthResponse> getLocations(
            @Header("Token") String token
    );

    @POST("/api/v1/auth/login")
    Call<AuthResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );
}
