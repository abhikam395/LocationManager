package com.example.locationmanager.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.locationmanager.models.sources.LocationRemoteDataSource;
import com.example.locationmanager.models.LocationResponse;

import javax.inject.Inject;

public class LocationRepository {

    private LocationRemoteDataSource locationRemoteDataSource;

    @Inject
    public LocationRepository(LocationRemoteDataSource locationRemoteDataSource) {
        this.locationRemoteDataSource = locationRemoteDataSource;
    }

    public MutableLiveData<LocationResponse> requestLocations(String token){
        MutableLiveData<LocationResponse> liveData = null;
        if(liveData == null){
            liveData = locationRemoteDataSource.requestUsersLocation(token);
        }
        return liveData;
    }

    public MutableLiveData<LocationResponse> requestLocationUpdate(String token, int userId, double latitude, double longitude){
        MutableLiveData<LocationResponse> liveData = null;
        if(liveData == null){
            liveData = locationRemoteDataSource.postUserLocation(token, userId, latitude, longitude);
        }
        return liveData;
    }
}
