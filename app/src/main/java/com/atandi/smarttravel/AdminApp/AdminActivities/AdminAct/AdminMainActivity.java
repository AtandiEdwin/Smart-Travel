package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.PaperComons.ADMIN_EMAIL;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Paper.init(AdminMainActivity.this);
        String adminMail = Paper.book().read(ADMIN_EMAIL);

        LinearLayout linearRouteManager = findViewById(R.id.linearRouteManager);
        LinearLayout linearActiveTracking = findViewById(R.id.linearActiveTracking);
        LinearLayout linearBookedCustomer = findViewById(R.id.linearBookedCustomer);
        LinearLayout linearVehicleRegistration = findViewById(R.id.linearVehicleRegistration);
        LinearLayout linearDriverRegistration = findViewById(R.id.linearDriverRegistration);
        LinearLayout linearUserNotification = findViewById(R.id.linearUserNotification);
        TextView userMail = findViewById(R.id.userMail);
        BottomNavigationView navigator = findViewById(R.id.adminnavigator);


        linearRouteManager.setOnClickListener(this);
        linearActiveTracking.setOnClickListener(this);
        linearBookedCustomer.setOnClickListener(this);
        linearVehicleRegistration.setOnClickListener(this);
        linearDriverRegistration.setOnClickListener(this);
        linearUserNotification.setOnClickListener(this);


//        give functionality to navigation view items
        navigator.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_share:
                        Toast.makeText(AdminMainActivity.this, "thank you for planning to share this app", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_rate:
                        Toast.makeText(AdminMainActivity.this, "thank you for planning to rate this app", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_admin:
                        startActivity(new Intent(AdminMainActivity.this, MainActivity.class));
                        finish();
                        break;

                    case R.id.action_logout:
                        Paper.book().destroy();
                        startActivity(new Intent(AdminMainActivity.this, AdminLoginActivity.class));
                        finish();
                        break;
                }
            }
        });

//        set welcome email
        if(adminMail!=null){
            if(!adminMail.isEmpty()){
                userMail.setText(adminMail);
            }
        }
        else{
            String admin = "Administrator";
            userMail.setText(admin);
        }
    }

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminMainActivity.this, AdminHomeActivity.class);

                if(v.getId()==R.id.linearRouteManager){
                    intent.putExtra("header","Route Management");
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
                    finish();
                }
                else if(v.getId()==R.id.linearActiveTracking){
                    startActivity(new Intent(AdminMainActivity.this,AdminMapsActivity.class));
                }

            }

    @Override
    public void onBackPressed() { finish();
    }
}