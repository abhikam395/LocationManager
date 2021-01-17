package com.example.locationmanager.models;

public class Chat {
//    public int id;
    public ChatUser from;
    public String message;
    public String time;
    public String date;
    public boolean hasSeen;

//    public Chat setId(int id) {
//        this.id = id;
//        return this;
//    }

    public Chat setFrom(ChatUser from) {
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
