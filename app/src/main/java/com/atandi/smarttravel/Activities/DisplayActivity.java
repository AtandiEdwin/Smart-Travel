package com.atandi.smarttravel.Activities;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminHomeActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminLoginActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
import com.atandi.smarttravel.EditFragments.TrackingTypeFragment;
import com.atandi.smarttravel.Fragments.AccountsFragment;
import com.atandi.smarttravel.Fragments.InfoFragment;
import com.atandi.smarttravel.Fragments.NotificationFragment;
import com.atandi.smarttravel.Fragments.RouteSelectingFragment;
import com.atandi.smarttravel.Fragments.TripsFragment;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;

import io.paperdb.Paper;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String title =getIntent().getStringExtra("header");
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);


        if(title == null){
            Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        }

        else if(title.equals("Vehicle Booking")){
            StartFragment(new RouteSelectingFragment(),"Vehicle Booking");
        }
        else if(title.equals("Trips")){
            StartFragment(new TripsFragment(),"Trips");
        }
        else if(title.equals("Tracker")){
            StartFragment(new TrackingTypeFragment(),"Tracker");
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(DisplayActivity.this, MainActivity.class));
        finish();
        return super.onSupportNavigateUp();

    }

    private void StartFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,title);
        fragmentTransaction.replace(R.id.Framelayoutid,fragment);
        fragmentTransaction.commit();
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_admin:
//                startActivity(new Intent(DisplayActivity.this, AdminMainActivity.class));
//                finish();
//                break;
//            case R.id.action_rate:
//                Toast.makeText(DisplayActivity.this, "thank you for planning to rate this app", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.action_share:
//                Toast.makeText(DisplayActivity.this, "thank you for planning to share this app", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.action_logout:
//                Paper.book().destroy();
//                startActivity(new Intent(DisplayActivity.this, LoginActivity.class));
//                finish();
//                break;
//            case R.id.daction_settings:
//                break;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DisplayActivity.this,MainActivity.class));
        finish();
    }
}
