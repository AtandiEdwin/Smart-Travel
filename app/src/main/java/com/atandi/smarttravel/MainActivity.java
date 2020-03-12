package com.atandi.smarttravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.DisplayActivity;
import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.Activities.MapsActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
import com.atandi.smarttravel.Models.User;
import com.atandi.smarttravel.UserServices.UserTrackerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.PaperComons.USER_NAME;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PASSWORD;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// initialize paper
        Paper.init(this);

// create references to the layout elements
        TextView adaptertext = findViewById(R.id.adaptext);
        final TextView userNames= findViewById(R.id.userName);
        BottomNavigationView navigator = findViewById(R.id.navigator);

        LinearLayout VehicleLinear = findViewById(R.id.linearVehicleBooking);
        LinearLayout TripsLinear = findViewById(R.id.linearTrips);
        LinearLayout TrackerLinear = findViewById(R.id.linearTracker);
        LinearLayout NotificationsLinear = findViewById(R.id.linearNotifications);
        LinearLayout AccountLinear = findViewById(R.id.linearAccount);
        LinearLayout InfoLinear = findViewById(R.id.linearInfo);

// add onclick listeners to the layouts in the main layout
        VehicleLinear.setOnClickListener(this);
        TripsLinear.setOnClickListener(this);
        TrackerLinear.setOnClickListener(this);
        NotificationsLinear.setOnClickListener(this);
        AccountLinear.setOnClickListener(this);
        InfoLinear.setOnClickListener(this);

// set the welcoming text
        adaptertext.setText(R.string.adap);

//  give functionality to the navigation view items(use item reselected to enable the user click the item twice before achieving the functionality)
        navigator.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_share:
                        Toast.makeText(MainActivity.this, "thank you for planning to share this app", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_rate:
                        Toast.makeText(MainActivity.this, "thank you for planning to rate this app", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_admin:
                        startActivity(new Intent(MainActivity.this, AdminMainActivity.class));
                        finish();
                        break;

                    case R.id.action_logout:
                        Paper.book().destroy();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
            }
        });


//    detemine which name is to be written in the header
        final String userPhone = Paper.book().read(USER_PHONE);
        String pwd = Paper.book().read(USER_PASSWORD);
        if(userPhone!=null && pwd!=null){
            if(!userPhone.isEmpty() && !pwd.isEmpty()){

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userPhone).exists()) {
                            User user = dataSnapshot.child(userPhone).getValue(User.class);
                            String headername= user.getUsername();
                            Paper.book().write(USER_NAME,headername);
                            userNames.setText(headername);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }
        else{
            String customer = "Customer";
            userNames.setText(customer);
        }
    }


//   this is implemented by the view.on click listener
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        if(v.getId()==R.id.linearVehicleBooking){
            intent.putExtra("header","Vehicle Booking");
        }
        else if(v.getId()==R.id.linearTrips){
            intent.putExtra("header","Trips");
        }

        else if (v.getId()==R.id.linearNotifications){
            intent.putExtra("header","Notifications");
        }
        else if(v.getId()==R.id.linearAccount){
            intent.putExtra("header","Account");
        }
        else if(v.getId()==R.id.linearInfo){
            intent.putExtra("header","Info Center");
        }

        startActivity(intent);
        finish();
        if(v.getId()==R.id.linearTracker){
            startActivity(new Intent(MainActivity.this, UserTrackerActivity.class));
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
