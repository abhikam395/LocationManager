package com.example.locationmanager.models;

import java.util.List;

public class Data {

    public String token;
    public UserResponse userResponse;
    public List<LocationData> locations;

    public String getToken(){
        return token;
    }

    public UserResponse getUser(){
        return userResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public List<LocationData> getLocations() {
        return locations;
    }
}

