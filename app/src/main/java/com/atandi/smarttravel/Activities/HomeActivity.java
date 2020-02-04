package com.atandi.smarttravel.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.atandi.smarttravel.Fragments.AccountsFragment;
import com.atandi.smarttravel.Fragments.InfoFragment;
import com.atandi.smarttravel.Fragments.NotificationFragment;
import com.atandi.smarttravel.Fragments.RouteSelectingFragment;
import com.atandi.smarttravel.Fragments.TripsFragment;
import com.atandi.smarttravel.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bundle = new Bundle();
        Intent intent = getIntent();
        String title = intent.getStringExtra("header");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("changed");
        setSupportActionBar(toolbar);

        if(title == null){

        }
        else if(title.equals("Vehicle Booking")){
            StartFragment(new RouteSelectingFragment(),"Vehicle Booking");
        }
        else if(title.equals("Trips")){
            StartFragment(new TripsFragment(),"Trips");
        }
        else if(title.equals("Tracker")){
            StartFragment(new RouteSelectingFragment(),"Tracker");
        }
        else if(title.equals("Notifications")){
            StartFragment(new NotificationFragment(),"Notifications");
        }
        else if(title.equals("Account")){
            StartFragment(new AccountsFragment(),"Account");
        }
        else if(title.equals("Info Center")){
            StartFragment(new InfoFragment(),"Info Center");
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tracker, R.id.nav_trip, R.id.nav_tracker,
                R.id.nav_notifications,R.id.nav_account,R.id.nav_info, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragmentOne);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void StartFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,title);
        fragmentTransaction.replace(R.id.nav_host_fragmentTwo,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragmentOne);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
