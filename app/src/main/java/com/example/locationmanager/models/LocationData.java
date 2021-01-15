package com.example.locationmanager.models;

public class LocationData {

    public int id;
    public int userId;
    public double latitude;
    public double longitude;
    public String updatedAt;

    public int getId(){
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getUserId() {
        return userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
