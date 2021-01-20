package com.example.locationmanager.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.locationmanager.R;
import com.example.locationmanager.helpers.LocationManager;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.ChatUser;
import com.example.locationmanager.models.LocationData;
import com.example.locationmanager.models.LocationResponse;
import com.example.locationmanager.models.LocationUser;
import com.example.locationmanager.services.LocationUpdatesService;
import com.example.locationmanager.utils.GlobalApplication;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.example.locationmanager.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "HomeActivity";

    private GoogleMap mGoogleMap;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.3
    private boolean mBound = false;

    private LinearLayout linearLayoutBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView txtCurrentLocation;
    private NavigationView navigationView;
    private CardView cardViewAllUsers;
    private CardView cardViewNearMe;
    private CardView cardViewProfile;
    private CardView cardViewSetting;
    private CardView cardViewLogout;
    private Button btnStartChat;
    private View header;
    private LatLng lastLocation;

    private SharePreferenceManager sharePreferenceManager;
    private List<LocationData> userLocatons;
    private AuthUser user;
    private ChatUser selectedUser;
    private TextView lblUserName, lblUserLocation;

    @Inject LocationViewModel locationViewModel;
    @Inject LocationManager locationManager;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestLocationUpdates();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
            mService.removeLocationUpdates();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((GlobalApplication)getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        setContentView(R.layout.activity_home);

        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init(){

        sharePreferenceManager = new SharePreferenceManager(this);

        user = sharePreferenceManager.getUser();
        userLocatons = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        header = navigationView.getHeaderView(0);
        txtCurrentLocation = header.findViewById(R.id.txt_current_location);
        cardViewAllUsers = header.findViewById(R.id.cardview_all_user);
        cardViewNearMe = header.findViewById(R.id.cardview_near_me);
        cardViewProfile = header.findViewById(R.id.cardview_profile);
        cardViewSetting = header.findViewById(R.id.cardview_setting);
        cardViewLogout = header.findViewById(R.id.cardview_logout);

        btnStartChat = findViewById(R.id.btn_start_chat);

        lblUserName = findViewById(R.id.lbl_name_bottom_sheet_home);
        lblUserLocation = findViewById(R.id.lbl_location_bottom_sheet_home);
        linearLayoutBottomSheet = findViewById(R.id.bottom_sheet_home);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomSheet);

        setListeners();
        callLocationApi();
    }

    private void setListeners(){
        cardViewAllUsers.setOnClickListener(this);
        cardViewNearMe.setOnClickListener(this);
        cardViewProfile.setOnClickListener(this);
        cardViewSetting.setOnClickListener(this);
        cardViewLogout.setOnClickListener(this);
        btnStartChat.setOnClickListener(this);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        Log.d(TAG, "onStateChanged: " + "HIDDEN");
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        Log.d(TAG, "onStateChanged: " + "EXPANDED");
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Log.d(TAG, "onStateChanged: " + "COLLAPSED");
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        Log.d(TAG, "onStateChanged: " + "DRAGGING");
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        Log.d(TAG, "onStateChanged: " + "SETTLING");
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setMapType(getMapType());
        lastLocation = sharePreferenceManager.getLastLocation();
        if(lastLocation != null){
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 18.0f));
            locationManager.addMarker(lastLocation, "My location", user.getId(), mGoogleMap);
        }
    }

    private int getMapType(){
        int mapType = sharePreferenceManager.getMapType();
        if(mapType != 0)
            return mapType;
        return 1;
    }

    //get users location
   private void callLocationApi() {
       String token = sharePreferenceManager.getToken();
       locationViewModel.getUsersLocation(token).observe(this, locationResponse -> {
           if (locationResponse.status) {
               userLocatons = locationResponse.getData().getLocations();
               locationManager.addMarkersToMap(mGoogleMap, userLocatons);
           } else
               Toast.makeText(getApplicationContext(), "Error got",
                       Toast.LENGTH_SHORT).show();
       });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Bind to the service. If the service is in foreground mode, this signals to the service
     * that since this activity is in the foreground, the service can exit foreground mode.
     */
    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item: {
                drawerLayout.openDrawer(Gravity.LEFT);
                return  true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalApplication.activityResumed();
        if(mGoogleMap != null) {
            mGoogleMap.setMapType(getMapType());
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(mService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        GlobalApplication.activityPaused();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    /**
     * Unbind from the service. This signals to the service that this activity is no longer
     * in the foreground, and the service can respond by promoting itself to a foreground service.
     *
     */
    @Override
    protected void onStop() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        sharePreferenceManager.setLastLocation(lastLocation);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_chat: {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("toUser", selectedUser);
                startActivity(intent);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            }
            case R.id.cardview_all_user: {
                Toast.makeText(this, "All users", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            }
            case R.id.cardview_near_me: {
                Toast.makeText(this, "Near Me", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            }
            case R.id.cardview_profile: {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            }
            case R.id.cardview_setting: {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(this, SettingActivity.class));
                break;
            }
            case R.id.cardview_logout: {
                sharePreferenceManager.clear();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerId = Integer.parseInt(marker.getSnippet());
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18.0f));
        if(markerId != user.getId()) {
            selectedUser = new ChatUser(Integer.parseInt(marker.getSnippet()), marker.getTitle());
            lblUserName.setText(selectedUser.name);
            locationManager.setAddress(lblUserLocation, marker.getPosition(), this);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        return true;
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(mService.EXTRA_LOCATION);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d(TAG, "onReceive: " + latLng);
            locationManager.setAddress(txtCurrentLocation, latLng, getApplicationContext());
            lastLocation = latLng;
            updateLocation(latLng);
        }
    }

    //update self location
    private void updateLocation(LatLng latLng){
        locationViewModel.updateUserLocation(sharePreferenceManager.getToken(),
                sharePreferenceManager.getUser().getId(), latLng.latitude, latLng.longitude).
                observe(this, locationResponse -> {
                    locationManager.addMarker(latLng, "My location", user.getId(), mGoogleMap);
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                });
    }
}