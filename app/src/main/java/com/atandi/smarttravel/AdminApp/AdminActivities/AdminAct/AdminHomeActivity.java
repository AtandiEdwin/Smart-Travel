package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import android.content.Intent;
import android.os.Bundle;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.ActiveTrackingFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.BookedCustomersFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.DriverRegistrationFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.RouteManagementFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.UserNotificationFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.VehicleRegistrationFragment;
import com.atandi.smarttravel.Fragments.AccountsFragment;
import com.atandi.smarttravel.Fragments.InfoFragment;
import com.atandi.smarttravel.Fragments.NotificationFragment;
import com.atandi.smarttravel.Fragments.RouteSelectingFragment;
import com.atandi.smarttravel.Fragments.TripsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.View;

import com.atandi.smarttravel.R;

public class AdminHomeActivity extends AppCompatActivity {

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

                bundle = new Bundle();
                Intent intent = getIntent();
                String title = intent.getStringExtra("header");


                if(title == null){

                }
                else if(title.equals("Route Management")){
                    StartFragment(new RouteManagementFragment(),"Route Management");
                }
                else if(title.equals("Active Tracking")){
                    StartFragment(new ActiveTrackingFragment(),"Active Tracking");
                }
                else if(title.equals("Booked Customers")){
                    StartFragment(new BookedCustomersFragment(),"Booked Customers");
                }
                else if(title.equals("Vehicle Registration")){
                    StartFragment(new VehicleRegistrationFragment(),"Vehicle Registration");
                }
                else if(title.equals("Driver Registration")){
                    StartFragment(new DriverRegistrationFragment(),"Driver Registration");
                }
                else if(title.equals("User Notification")){
                    StartFragment(new UserNotificationFragment(),"User Notification");
                }

            }

            private void StartFragment(Fragment fragment, String title) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment,title);
                fragmentTransaction.replace(R.id.adminNav_one_fragmentTwo,fragment);
                fragmentTransaction.commit();
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.home, menu);
                return true;
            }

}
