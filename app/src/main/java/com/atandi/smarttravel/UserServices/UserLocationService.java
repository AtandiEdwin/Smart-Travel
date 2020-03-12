package com.atandi.smarttravel.UserServices;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.atandi.smarttravel.Activities.MapsActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.Services.TrackingService;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;

@SuppressLint("Registered")
public class UserLocationService extends Service {
        private static final String TAG = UserTrackerActivity.class.getSimpleName();

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            buildNotification();
            Paper.init(UserLocationService.this);
            final String userphone = "0726123366";
//                    Paper.book().read(USER_PHONE);
            requestLocationUpdates(userphone);
}

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

        private void requestLocationUpdates(final String userphone) {
            LocationRequest request = new LocationRequest();
//            request.setInterval(10000);

            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            final String path = getString(R.string.firebase_path);
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {

                client.requestLocationUpdates(request, new LocationCallback() {
                    @Override
                    public void onLocationResult(final LocationResult locationResult) {
                        if(!userphone.isEmpty()) {
                            Toast.makeText(UserLocationService.this, "okay", Toast.LENGTH_SHORT).show();
                            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer").child(userphone);
                            Location location = locationResult.getLastLocation();
                            if (location != null) {
                                ref.child(path).setValue(location);

                            }
                        }
                        else{
                            MyBuilderClass myBuilderClass = new MyBuilderClass();
                            myBuilderClass.MyBuilder(UserLocationService.this,"server is unable to recognize your phone number");
                        }
                    }
                }, null);
            }
        }
}
