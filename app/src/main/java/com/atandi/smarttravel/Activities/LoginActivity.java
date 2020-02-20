package com.atandi.smarttravel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                final String mail = loginEmailUserId.getText().toString();
                final String password = loginPasswordUserId.getText().toString();



                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.child(mail).getValue(User.class);

                        if(user.getUserpassword().equals(password)){
                            Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                if(TextUtils.isEmpty(mail)|| TextUtils.isEmpty(password)){
//                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
//                }
//                else if(mail.equals(myemail[0]) && password.equals(mypassword[0])){
//                    Toast.makeText(LoginActivity.this, "okay", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if(task.isComplete()){
//
//                            }
//                            else{
//
//                            }
//
//                        }
//                    });
//
//                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {

    }
}
