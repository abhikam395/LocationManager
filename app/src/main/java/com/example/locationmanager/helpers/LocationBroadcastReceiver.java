package com.example.locationmanager.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class LocationBroadcastReceiver extends BroadcastReceiver {

    private LocationManager locationManager;

    @Inject
    public LocationBroadcastReceiver(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        Location location = intent.getParcelableExtra(mService.EXTRA_LOCATION);
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        locationManager.setAddress(txtCurrentLocation, latLng, getApplicationContext());
//        lastLocation = latLng;
//        updateLocation(latLng);
//        locationManager.addMarkersToMap(h);
    }
}
