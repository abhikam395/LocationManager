package com.example.locationmanager.models;

public class AuthResponse {
    public boolean status;
    public Data data;
    public Error error;

    public boolean getStatus(){
        return status;
    }

    public Data getData(){
        return data;
    }

    public Error getError(){
        return error;
    }
}
