package com.example.locationmanager.models;

import java.io.Serializable;

public class ChatUser implements Serializable {
    public int id;
    public String name;

    public ChatUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
