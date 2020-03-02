package com.atandi.smarttravel.AdminApp.AdminActivities.Services;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.Vehicle;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackingService extends Service {

        private static final String TAG = TrackingService.class.getSimpleName();

    @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            buildNotification();
            LocalBroadcastManager.getInstance(TrackingService.this).registerReceiver(mMessageReceiver,new IntentFilter("custom-message"));
//            requestLocationUpdates();
        }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String finalplate = intent.getStringExtra("number");
            requestLocationUpdates(finalplate);
        }
    };

    //Create the persistent notification//

        private void buildNotification() {
            String stop = "stop";
            registerReceiver(stopReceiver, new IntentFilter(stop));
            PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                    this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);

// Create the persistent notification//
            Notification.Builder builder = new Notification.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.tracking_enabled_notif))

//Make this notification ongoing so it can’t be dismissed by the user//

                    .setOngoing(true)
                    .setContentIntent(broadcastIntent)
                    .setSmallIcon(R.drawable.ic_tracon);
            startForeground(1, builder.build());
        }

        protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiver(stopReceiver);
                stopSelf();
            }
        };



        private void requestLocationUpdates(final String finalplate) {
            LocationRequest request = new LocationRequest();
            request.setInterval(10000);

            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            final String path = getString(R.string.firebase_path);
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {

                client.requestLocationUpdates(request, new LocationCallback() {
                    @Override
                    public void onLocationResult(final LocationResult locationResult) {
                        if(!finalplate.isEmpty()) {
                            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle").child(finalplate).child(path);
                            Location location = locationResult.getLastLocation();
                            if (location != null) {
                                ref.setValue(location);
                            }
                        }
                        else{
                            MyBuilderClass myBuilderClass = new MyBuilderClass();
                            myBuilderClass.MyBuilder(TrackingService.this,"server is unable to recognize the plate number");
                        }
                    }
                }, null);
            }
        }
}
