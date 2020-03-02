package com.atandi.smarttravel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminLoginActivity;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminMainActivity;
import com.atandi.smarttravel.Constants.MyBuilderClass;
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

public class LoginActivity extends AppCompatActivity {

    EditText loginEmailUserId,loginPasswordUserId;
    Button Btnlogin;
    ImageButton BtnGoAdmin;
    TextView toRegister;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

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
        BtnGoAdmin = findViewById(R.id.BtnGoAdmin);


        BtnGoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AdminLoginActivity.class));
                finish();
            }
        });


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
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final String mail = loginEmailUserId.getText().toString();
                final String password = loginPasswordUserId.getText().toString();


                if (mail.isEmpty() || password.isEmpty()) {

                    progressDialog.dismiss();
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(LoginActivity.this,"All fields are required");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.child(mail).getValue(User.class);

                            if (user == null) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            } else {
                                if (user.getUserpassword().equals(password)) {
                                    progressDialog.dismiss();
                                    String userName = user.getUsername();

                                    //bradcast user phone number
                                    Intent broad = new Intent("Number");
                                    broad.putExtra("number", mail);
                                    broad.putExtra("name", userName);
                                    LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(broad);

                                    //pass user name to main page
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }

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
