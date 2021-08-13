package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.atandi.smarttravel.Activities.DisplayActivity;
import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.ActiveTrackingFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.AssignKeyFragment;
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
import com.atandi.smarttravel.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.atandi.smarttravel.R;

import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

                Intent intent = getIntent();
                String title = intent.getStringExtra("header");
                Toolbar toolbar = findViewById(R.id.admintoolbar);
                toolbar.setTitle(title);
                toolbar.setTitleTextColor(Color.WHITE);
                toolbar.setNavigationIcon(R.drawable.ic_back);
                setSupportActionBar(toolbar);


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
                else if(title.equals("Assign Key")){
                    StartFragment(new AssignKeyFragment(),"Assign Key");
                }

            }

            private void StartFragment(Fragment fragment, String title) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment,title);
                fragmentTransaction.replace(R.id.adminFramelayoutid,fragment);
                fragmentTransaction.commit();
            }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(AdminHomeActivity.this, AdminMainActivity.class));
        finish();
        return super.onSupportNavigateUp();

    }

//            @Override
//            public boolean onCreateOptionsMenu(Menu menu) {
//                getMenuInflater().inflate(R.menu.admin_menu, menu);
//                return true;
//            }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_customer:
//                startActivity(new Intent(AdminHomeActivity.this,MainActivity.class));
//                finish();
//                break;
//            case R.id.action_rate:
//                Toast.makeText(AdminHomeActivity.this, "thank you for planning to rate this app", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.action_share:
//                Toast.makeText(AdminHomeActivity.this, "thank you for planning to share this app", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.action_logout:
//                Paper.book().destroy();
//                startActivity(new Intent(AdminHomeActivity.this, AdminLoginActivity.class));
//                finish();
//                break;
//            case R.id.daction_settings:
//                break;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminHomeActivity.this,AdminMainActivity.class));
        finish();
    }
}
