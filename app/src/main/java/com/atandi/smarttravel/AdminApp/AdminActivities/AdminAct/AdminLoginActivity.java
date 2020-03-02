package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.Activities.LoginActivity;
import com.atandi.smarttravel.Activities.RegisterActivity;
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

public class AdminLoginActivity extends AppCompatActivity {

    EditText loginEmailUserId,loginPasswordUserId;
    Button Btnlogin;
    TextView toRegister;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent =  new Intent(AdminLoginActivity.this, AdminMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


                mAuth = FirebaseAuth.getInstance();
                firebaseUser = mAuth.getCurrentUser();

                loginEmailUserId = findViewById(R.id.loginEmailUserId);
                loginPasswordUserId = findViewById(R.id.loginPasswordUserId);
                Btnlogin = findViewById(R.id.Btnlogin);

                Btnlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        progressDialog = new ProgressDialog(AdminLoginActivity.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_layout);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        final String mail = loginEmailUserId.getText().toString();
                        final String password = loginPasswordUserId.getText().toString();

                       mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                String userMail = mail;
                                Intent intent =  new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("User",userMail);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(AdminLoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                });
            }
}
