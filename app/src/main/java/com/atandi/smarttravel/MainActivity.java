package com.atandi.smarttravel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.atandi.smarttravel.Activities.HomeActivity;
import com.atandi.smarttravel.Activities.MapsActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private AdapterViewFlipper adapterViewFlipper;
    private static final String[] TEXT = {"vbooking","trips","tracker","notification",
    "account","info"
    };
    private static final int[] IMAGES = {R.drawable.ic_car,R.drawable.ic_trip,R.drawable.ic_tracker,R.drawable.ic_notifications
    ,R.drawable.ic_account,R.drawable.ic_info
    };

    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterViewFlipper = findViewById(R.id.adapterFlipperId);

        Button goAdmin  = findViewById(R.id.goId);
        goAdmin.setOnClickListener(this);

//        FlipperAdapter adapter = new FlipperAdapter(this,IMAGES,TEXT);
//        adapterViewFlipper.setAdapter(adapter);
//        adapterViewFlipper.setAutoStart(true);

        LinearLayout VehicleLinear = findViewById(R.id.linearVehicleBooking);
        LinearLayout TripsLinear = findViewById(R.id.linearTrips);
        LinearLayout TrackerLinear = findViewById(R.id.linearTracker);
        LinearLayout NotificationsLinear = findViewById(R.id.linearNotifications);
        LinearLayout AccountLinear = findViewById(R.id.linearAccount);
        LinearLayout InfoLinear = findViewById(R.id.linearInfo);

        VehicleLinear.setOnClickListener(this);
        TripsLinear.setOnClickListener(this);
        TrackerLinear.setOnClickListener(this);
        NotificationsLinear.setOnClickListener(this);
        AccountLinear.setOnClickListener(this);
        InfoLinear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);

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

        if(v.getId()==R.id.goId){
            startActivity(new Intent(MainActivity.this, AdminMainActivity.class));
        }
        else if(v.getId()==R.id.linearTracker){
            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        }

    }

    class FlipperAdapter extends BaseAdapter{
        Context context;
        int[] images;
        String[] names;
        LayoutInflater inflater;

        public FlipperAdapter(Context context, int[] images, String[] names) {
            this.context = context;
            this.images = images;
            this.names = names;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(R.layout.flipper_items,null);
            TextView textView = convertView.findViewById(R.id.TextViewId);
            ImageView imageView = convertView.findViewById(R.id.ImageViewId);

            textView.setText(names[position]);
            imageView.setImageResource(images[position]);

            return convertView;
        }
    }


}
