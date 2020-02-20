package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.Activities.RegisterActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.Vehicle;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.Models.User;
import com.atandi.smarttravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VehicleActivity extends AppCompatActivity {
    EditText vloginPlateId,vloginPasswordId;
    Button Btnlogin;

    private FirebaseAuth mAuth;
    private static String plate= "";

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null){
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);


                mAuth = FirebaseAuth.getInstance();

                vloginPlateId = findViewById(R.id.vloginPlateId);
                vloginPasswordId = findViewById(R.id.vloginPasswordId);
                Btnlogin = findViewById(R.id.Btnlogin);


                Btnlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plate= vloginPlateId.getText().toString();
                        final String password = vloginPasswordId.getText().toString();

                        if(plate.isEmpty()){
                            Toast.makeText(VehicleActivity.this, "please provide the vehicle number", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(password.isEmpty()){
                                Toast.makeText(VehicleActivity.this, "Provide password to proceed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vehicle");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Vehicle vehicle = dataSnapshot.child(plate).getValue(Vehicle.class);

                                        assert vehicle != null;
                                        if(vehicle.getVehicle_plate().equals(plate)){
                                            if(vehicle.getVehicle_password().equals(password)){
                                                Intent intent =  new Intent(VehicleActivity.this,TrackerActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("plates","kvc 234h");
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(VehicleActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(VehicleActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }
                });
            }

            private void updateUI(FirebaseUser user) {


    }
}
