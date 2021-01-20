package com.example.locationmanager.helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;

import com.example.locationmanager.models.LocationData;
import com.example.locationmanager.models.LocationUser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class LocationManager {


    @Inject
    public LocationManager() {
    }

    /**
     * get address of Latlog from Geocoder and set it on TextView
     * @param lblUserAddress
     * @param latLng
     * @param context
     */
    public void setAddress(TextView lblUserAddress, LatLng latLng, Context context){
        Geocoder geocoder;
        final List<Address>[] addresses = new List[]{new ArrayList<>()};
        geocoder = new Geocoder(context, Locale.getDefault());

        Thread thread = new Thread(() -> {
            try {
                addresses[0] = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String address = addresses[0].get(0).getAddressLine(0);

                lblUserAddress.post(() -> lblUserAddress.setText(address));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void addMarker(LatLng latLng, String title, int userId, GoogleMap googleMap){
        if(latLng == null)
            return;
        googleMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(String.valueOf(userId))).showInfoWindow();
    }

    public void addMarkersToMap(GoogleMap googleMap, List<LocationData> userLocations){
        googleMap.clear();
        for(LocationData locationData : userLocations){
            LocationUser user = locationData.getUser();
            LatLng latLng = new LatLng(locationData.getLatitude(), locationData.getLongitude());
            addMarker(latLng, user.name, user.id, googleMap);
        }
    }
}
