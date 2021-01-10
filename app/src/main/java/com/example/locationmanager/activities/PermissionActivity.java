package com.example.locationmanager.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.locationmanager.R;

public class PermissionActivity extends AppCompatActivity {

    private ImageView imgViewIcon;
    private Button btnPermissionAllow;
    private static final String TAG = "PermissionActivity";
    private final String[] permissions = { Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private final int LOCATION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        imgViewIcon = findViewById(R.id.permission_img_icon);
        btnPermissionAllow = findViewById(R.id.permission_allow_btn);

        imgViewIcon.setImageDrawable(getDrawable(R.mipmap.location_icon_background));
        btnPermissionAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestLocationPermissions();
            }
        });
    }

    /**
     * check permissions and request if not granted
     */
    private void checkAndRequestLocationPermissions(){
        if(checkPermissionStatus(this, permissions)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(permissions, LOCATION_CODE);
    }

    /**
     * check permissions status
     * @param context
     * @param permissions
     * @return
     */
    private boolean checkPermissionStatus(Context context, String[] permissions){
        if(context != null & permissions.length > 0){
            for (String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "checkPermissionStatus: " + false);
                    return false;
                }
                Log.d(TAG, "checkPermissionStatus: " + true);
            }
        }
        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }  else {
                    Toast.makeText(PermissionActivity.this, "Permission not allowed", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }
}