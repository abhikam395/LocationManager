package com.example.locationmanager.services;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    public Retrofit getAuthService(){
        return new Retrofit.Builder()
                .baseUrl("http://13.233.93.124:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
