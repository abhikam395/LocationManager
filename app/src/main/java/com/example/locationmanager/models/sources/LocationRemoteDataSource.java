package com.example.locationmanager.models.sources;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.locationmanager.models.LocationResponse;
import com.example.locationmanager.services.LocationService;
import com.google.firebase.database.MutableData;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationRemoteDataSource {

    private final Retrofit retrofit;
    private final LocationService locationService;

    @Inject
    public LocationRemoteDataSource(Retrofit retrofit) {
        this.retrofit = retrofit;
        this.locationService = retrofit.create(LocationService.class);
    }

    public MutableLiveData<LocationResponse> requestUsersLocation(String token){
        final MutableLiveData<LocationResponse> mutableLiveData = new MutableLiveData<>();
        locationService.getLocations(token).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<LocationResponse> postUserLocation(String token, int userId, double latitude, double longitude){
        final MutableLiveData<LocationResponse> mutableLiveData = new MutableLiveData<>();
        locationService.updateLocation(token, userId, latitude, longitude).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
