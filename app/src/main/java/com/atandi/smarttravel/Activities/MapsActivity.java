package com.atandi.smarttravel.Activities;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap = googleMap;
//first we initialize the location latitudes and longitudes as doubles since they can contain decimals
        final Double[] latitu = {0.1769};
        final Double[] longitu = {37.9083};
        final String path = getString(R.string.firebase_path);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(path);// meant to access the firebase database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                fetch the data and map them again to the variables
                latitu[0] = (Double) dataSnapshot.child("latitude").getValue();
                longitu[0] = (Double) dataSnapshot.child("longitude").getValue();

                LatLng vehiceLocation = new LatLng(latitu[0], longitu[0]);

                MarkerOptions mop = new MarkerOptions();
                mop.position(vehiceLocation);
                mop.title("Vehicle");
                mop.icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_iconlocatedriver));
                mMap.addMarker(mop);

                MarkerOptions user = new MarkerOptions();
                user.position( new LatLng(-0.544552,37.455666));
                user.title("user");
                mMap.addMarker(user);


                mMap.moveCamera(CameraUpdateFactory.newLatLng(vehiceLocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vehiceLocation,13f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Exception FB",databaseError.toException());
            }
        });
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsActivity.this, MainActivity.class));
        finish();
    }
}
