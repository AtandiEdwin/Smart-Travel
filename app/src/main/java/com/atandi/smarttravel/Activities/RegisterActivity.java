package com.atandi.smarttravel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextView toLogin;
    Button BtnRegister;
    EditText customerPasswordId,customerNumberId,customerUserNameId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = findViewById(R.id.toLoin);
        BtnRegister  =findViewById(R.id.BtnRegister);
        customerPasswordId = findViewById(R.id.customerPasswordId);
        customerNumberId = findViewById(R.id.customerNumberId);
        customerUserNameId =findViewById(R.id.customerUserNameId);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

      BtnRegister.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
              progressDialog = new ProgressDialog(RegisterActivity.this);
              progressDialog.show();
              progressDialog.setContentView(R.layout.progress_layout);
              progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
              final String customerPhone = customerNumberId.getText().toString();
              final String password = customerPasswordId.getText().toString();
              final String username = customerUserNameId.getText().toString();

              if (TextUtils.isEmpty(customerPhone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                  progressDialog.dismiss();
                  Toast.makeText(RegisterActivity.this, "All fields", Toast.LENGTH_SHORT).show();
              } else if (password.length() < 6) {
                  progressDialog.dismiss();
                  Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
              } else {
                  register(customerPhone,username,password);
              }
          }
      });
    }

    public void register(final String customerphone, final String musername, final String mpassword){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if(dataSnapshot.child(customerphone).exists()){
                    Toast.makeText(RegisterActivity.this, "User number already registered try changing your phone number", Toast.LENGTH_SHORT).show();
                }

                else{
                    User user = new User(musername,mpassword);
                    reference.child(customerphone).setValue(user);
                    Toast.makeText(RegisterActivity.this, "you were registered successifully now login", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
