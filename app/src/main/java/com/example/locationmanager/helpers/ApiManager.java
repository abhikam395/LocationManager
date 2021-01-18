package com.example.locationmanager.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.locationmanager.models.LocationResponse;
import com.example.locationmanager.services.LocationInterface;
import com.example.locationmanager.services.RestClient;
import com.example.locationmanager.utils.GlobalApplication;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private static final String TAG = "ApiManager";
    private SharePreferenceManager sharePreferenceManager;

    public ApiManager(){
//        this.sharePreferenceManager = GlobalApplication.c;
    }

    //get users location
    public static void callLocationApi(LatLng latLng){
        Log.d(TAG, "callLocationApi: " + latLng);
        LocationInterface locationInterface = RestClient.getClient().create(LocationInterface.class);
        Call<LocationResponse> call = locationInterface.updateLocation(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjo0LCJuYW1lIjoiU29udSIsImVtYWlsIjoic29udWt1bWFyQGdtYWlsLmNvbSJ9LCJpYXQiOjE2MTA5NzY5NjR9.StGM2VzZOemvRxrzzcLkEojCL_aljoYUybNTSc05DRA"
                , 1, latLng.latitude, latLng.longitude);
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                LocationResponse locationResponse = response.body();
                Log.d(TAG, "onResponse: " + call.request());
                if(locationResponse.isStatus()){
                    Log.d(TAG, "onResponse: " + locationResponse);
                    Log.d(TAG, "onResponse: " + locationResponse.getMessage());
                }
                else
                    Log.d(TAG, "onResponse: " + response.body().getMessage());
//                    Toast.makeText(A,"Error got",
//                            Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
    }
}
