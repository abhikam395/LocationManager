package com.example.locationmanager.models;

public class Chat {
    public int id;
    public String from;
    public String message;
    public String time;
    public String date;

    public Chat setId(int id) {
        this.id = id;
        return this;
    }

    public Chat setFrom(String from) {
        this.from = from;
        return this;
    }

    public Chat setMessage(String message) {
        this.message = message;
        return this;
    }

    public Chat setTime(String time) {
        this.time = time;
        return this;
    }

    public Chat setDate(String date) {
        this.date = date;
        return this;
    }
}
