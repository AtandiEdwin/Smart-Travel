package com.atandi.smarttravel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
import com.atandi.smarttravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView toLoin;
    Button BtnRegister;
    EditText passwordUserId,emailUserId,userNameId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        toLoin = findViewById(R.id.toLoin);
        BtnRegister  =findViewById(R.id.BtnRegister);
        passwordUserId = findViewById(R.id.passwordUserId);
        emailUserId = findViewById(R.id.emailUserId);
        userNameId =findViewById(R.id.userNameId);



        toLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

      BtnRegister.setOnClickListener(new View.OnClickListener() {
          final String email = emailUserId.getText().toString();
          final String password = passwordUserId.getText().toString();
          @Override
          public void onClick(View v) {

              if(email.isEmpty() && password.isEmpty()){
                  if(email.isEmpty()){
                      Toast.makeText(RegisterActivity.this, "eemeail", Toast.LENGTH_SHORT).show();
                  }
                  else if (password.isEmpty()){
                      Toast.makeText(RegisterActivity.this, "xxx", Toast.LENGTH_SHORT).show();
                  }
                  Toast.makeText(RegisterActivity.this, "check", Toast.LENGTH_SHORT).show();
              }
         else{
                  mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                          if(task.isComplete()){
                              startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                              finish();
                          }
                          else{
                              Toast.makeText(RegisterActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                          }

                      }
                  });
              }
          }
      });
    }
}
