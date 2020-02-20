package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.HomeActivity;
import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

                LinearLayout linearRouteManager = findViewById(R.id.linearRouteManager);
                LinearLayout linearActiveTracking = findViewById(R.id.linearActiveTracking);
                LinearLayout linearBookedCustomer = findViewById(R.id.linearBookedCustomer);
                LinearLayout linearVehicleRegistration = findViewById(R.id.linearVehicleRegistration);
                LinearLayout linearDriverRegistration = findViewById(R.id.linearDriverRegistration);
                LinearLayout linearUserNotification = findViewById(R.id.linearUserNotification);

      Button AdminLogout = findViewById(R.id.AdminLogout);

      AdminLogout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FirebaseAuth.getInstance().signOut();

// this listener will be called when there is change in firebase user session
              FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                  @Override
                  public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                      FirebaseUser user = firebaseAuth.getCurrentUser();
                      if (user == null) {
                          // user auth state is changed - user is null
                          // launch login activity
                          Toast.makeText(AdminMainActivity.this, "you are logged out", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                          finish();
                      }
                  }
              };
          }
      });

        linearRouteManager.setOnClickListener(this);
        linearActiveTracking.setOnClickListener(this);
        linearBookedCustomer.setOnClickListener(this);
        linearVehicleRegistration.setOnClickListener(this);
        linearDriverRegistration.setOnClickListener(this);
        linearUserNotification.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminMainActivity.this, AdminHomeActivity.class);

                if(v.getId()==R.id.linearRouteManager){
                    intent.putExtra("header","Route Management");
                }
                else if(v.getId()==R.id.linearActiveTracking){
                    intent.putExtra("header","Active Tracking");
                }
                else if(v.getId()==R.id.linearBookedCustomer){
                    intent.putExtra("header","Booked Customers");
                }
                else if (v.getId()==R.id.linearVehicleRegistration){
                    intent.putExtra("header","Vehicle Registration");
                }
                else if(v.getId()==R.id.linearDriverRegistration){
                    intent.putExtra("header","Driver Registration");
                }
                startActivity(intent);

                if(v.getId()==R.id.linearUserNotification){
                    startActivity(new Intent(AdminMainActivity.this,VehicleActivity.class));
                }

            }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminMainActivity.this,MainActivity.class));
        finish();
    }
}