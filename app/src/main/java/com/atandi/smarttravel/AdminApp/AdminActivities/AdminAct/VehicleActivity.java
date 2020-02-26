package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.Vehicle;
import com.atandi.smarttravel.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VehicleActivity extends AppCompatActivity {
    EditText vloginPlateId;
    Button Btnlogin;

    ProgressDialog progressDialog;


    private FirebaseAuth mAuth;
    private static String plate= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

                mAuth = FirebaseAuth.getInstance();

                vloginPlateId = findViewById(R.id.vloginPlateId);
                Btnlogin = findViewById(R.id.Btnlogin);

                Btnlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plate= vloginPlateId.getText().toString().toUpperCase();

                        if(plate.isEmpty()){
                            Toast.makeText(VehicleActivity.this, "please provide the vehicle number", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            progressDialog = new ProgressDialog(VehicleActivity.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_layout);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vehicle");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Vehicle vehicle = dataSnapshot.child(plate).getValue(Vehicle.class);


                                        assert vehicle != null;

                                        if(vehicle.getVehicle_plate().equals(plate)){
                                                String myplate = vehicle.getVehicle_plate();
                                                progressDialog.dismiss();
                                                Intent intent =  new Intent(VehicleActivity.this,TrackerActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("plates",myplate);
                                                startActivity(intent);
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
                });
            }
}
