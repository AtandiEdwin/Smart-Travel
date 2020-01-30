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

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
import com.atandi.smarttravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmailUserId,loginPasswordUserId;
    Button Btnlogin;
    TextView toRegister;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmailUserId = findViewById(R.id.loginEmailUserId);
        loginPasswordUserId = findViewById(R.id.loginPasswordUserId);
        Btnlogin = findViewById(R.id.Btnlogin);
        toRegister = findViewById(R.id.toRegister);



        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = loginEmailUserId.getText().toString();
                final String password = loginPasswordUserId.getText().toString();
                mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isComplete()){
                           startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                           finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });





    }

    private void updateUI(FirebaseUser user) {

    }
}
