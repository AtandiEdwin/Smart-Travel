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

    TextView toLoin;
    Button BtnRegister;
    EditText RpasswordUserId,RemailUserId,RuserNameId;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        toLoin = findViewById(R.id.toLoin);
        BtnRegister  =findViewById(R.id.BtnRegister);
        RpasswordUserId = findViewById(R.id.RpasswordUserId);
        RemailUserId = findViewById(R.id.RemailUserId);
        RuserNameId =findViewById(R.id.RuserNameId);



        toLoin.setOnClickListener(new View.OnClickListener() {
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
              final String email = RemailUserId.getText().toString();
              final String password = RpasswordUserId.getText().toString();
              final String username = RuserNameId.getText().toString();

              if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                  Toast.makeText(RegisterActivity.this, "All fields", Toast.LENGTH_SHORT).show();
              } else if (password.length() < 6) {
                  Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
              } else {
                  register(email, password, username);
              }
          }
      });
    }

    public void register(String memail,String mpassword, final String musername){

        User user = new User(musername,memail,mpassword);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");

        reference.child(memail).setValue(user);
        progressDialog.dismiss();
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();

    }
}
