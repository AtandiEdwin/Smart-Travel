package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.MapsActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.Services.TrackingService;
import com.atandi.smarttravel.R;

public class TrackerActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        String myplate = getIntent().getStringExtra("plates");

        Intent mintent = new Intent("custom-message");
        mintent.putExtra("number",myplate);
        LocalBroadcastManager.getInstance(TrackerActivity.this).sendBroadcast(mintent);

//Check whether GPS tracking is enabled//

                LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    finish();
                }

//Check whether this app has access to the location permission//

                int permission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

//If the location permission has been granted, then start the TrackerService//
//

                if (permission == PackageManager.PERMISSION_GRANTED) {
                    startTrackerService();
                } else {

//If the app doesn’t currently have access to the user’s location, then request access//

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST);
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
                    grantResults) {

                if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackerService();
                } else {

                    Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
                }
            }

            private void startTrackerService() {

                startService(new Intent(this, TrackingService.class));
                Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
                finish();
    }
}
