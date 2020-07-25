package com.atandi.smarttravel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.R;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

public class Splash1Activity extends AppCompatActivity {
    CircularFillableLoaders circularFillableLoaders;
    int progress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash1Activity.this,
                        MainActivity.class));
                finish();
            }
        }, secondsDelayed * 5000);

        circularFillableLoaders = findViewById(R.id.circularFillableLoaders);
        circularFillableLoaders.setProgress(progress, 10000);

    }
}
