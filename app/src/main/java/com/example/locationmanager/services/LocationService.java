package com.example.locationmanager.services;

import com.example.locationmanager.models.AuthResponse;
import com.example.locationmanager.models.LocationResponse;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

@Singleton
public interface LocationService {
    @GET("/api/v1/location")
    Call<LocationResponse> getLocations(
            @Header("Token") String token
    );

    @POST("/api/v1/location")
    Call<LocationResponse> updateLocation(
            @Header("Token") String token,
            @Query("userId") int userId,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
    );
}
