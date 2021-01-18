package com.example.locationmanager.models;

public class LocationResponse {

    public boolean status;
    public String message;
    public Data data;

    public boolean isStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
