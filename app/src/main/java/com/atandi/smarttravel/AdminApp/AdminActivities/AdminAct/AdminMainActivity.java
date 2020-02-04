package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atandi.smarttravel.Activities.HomeActivity;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;

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
                    startActivity(new Intent(AdminMainActivity.this,TrackerActivity.class));
                }

            }

}