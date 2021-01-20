package com.example.locationmanager.dagger;

import com.example.locationmanager.activities.HomeActivity;
import com.example.locationmanager.activities.LoginActivity;
import com.example.locationmanager.activities.RegisterActivity;
import com.example.locationmanager.services.LocationUpdatesService;
import com.example.locationmanager.services.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(LocationUpdatesService locationUpdatesService);
    void inject(HomeActivity homeActivity);
}
