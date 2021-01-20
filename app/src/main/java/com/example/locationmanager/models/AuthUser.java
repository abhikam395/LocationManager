package com.example.locationmanager.models;

import android.util.Log;

public class AuthUser {
    public int id;
    public String name;
    public String email;

    public AuthUser(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
