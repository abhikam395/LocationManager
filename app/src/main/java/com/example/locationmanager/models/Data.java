package com.example.locationmanager.models;

import java.util.List;

public class Data {

    public String token;
    public UserResponse user;
    public List<LocationData> locations;

    public String getToken(){
        return token;
    }

    public UserResponse getUser(){
        return user;
    }

    public List<LocationData> getLocations() {
        return locations;
    }
}

