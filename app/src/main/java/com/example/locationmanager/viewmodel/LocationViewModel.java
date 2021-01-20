package com.example.locationmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationmanager.models.LocationResponse;
import com.example.locationmanager.models.repositories.LocationRepository;

import javax.inject.Inject;

public class LocationViewModel extends ViewModel {

    private LocationRepository locationRepository;

    @Inject
    public LocationViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public MutableLiveData<LocationResponse> getUsersLocation(String token){
        MutableLiveData<LocationResponse> mutableLiveData = null;
        if(mutableLiveData == null){
            mutableLiveData = locationRepository.requestLocations(token);
        }
        return mutableLiveData;
    }

    public MutableLiveData<LocationResponse> updateUserLocation(String token, int userId, double latitude, double longitude){
        MutableLiveData<LocationResponse> mutableLiveData = null;
        if(mutableLiveData == null){
            mutableLiveData = locationRepository.requestLocationUpdate(token, userId, latitude, longitude);
        }
        return mutableLiveData;
    }
}
