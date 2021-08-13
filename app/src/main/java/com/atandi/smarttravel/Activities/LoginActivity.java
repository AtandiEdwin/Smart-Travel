package com.atandi.smarttravel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAct.AdminLoginActivity;

import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.MainActivity;
import com.atandi.smarttravel.Models.User;
import com.atandi.smarttravel.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.PaperComons.USER_PASSWORD;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;

public class LoginActivity extends AppCompatActivity {

    EditText userPhoneNumber,userloginPasswordId;
    Button Btnlogin;
    ImageButton BtnGoAdmin;
    TextView toRegister;
    ProgressDialog progressDialog;
    CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userloginPasswordId = findViewById(R.id.userloginPasswordId);
        Btnlogin = findViewById(R.id.Btnlogin);
        toRegister = findViewById(R.id.toRegister);
        BtnGoAdmin = findViewById(R.id.BtnGoAdmin);
        rememberMe = findViewById(R.id.rememberMe);

// init paper
        Paper.init(this);

        String userPhone = Paper.book().read(USER_PHONE);
        String pwd = Paper.book().read(USER_PASSWORD);

//        check paper before requesting login
        if(userPhone!=null && pwd!=null){
            if(!userPhone.isEmpty() && !pwd.isEmpty()){
                login(userPhone,pwd);
            }
        }

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
                final String userPhone = userPhoneNumber.getText().toString();
                final String userpassword = userloginPasswordId.getText().toString();

                if (userPhone.isEmpty() || userpassword.isEmpty()) {
                    progressDialog.dismiss();
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(LoginActivity.this,"All fields are required");
                } else {

                    //save to paper

                    if(rememberMe.isChecked()){
                        Paper.book().write(USER_PHONE,userPhone);
                        Paper.book().write(USER_PASSWORD,userpassword);
                    }


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(userPhone).exists()) {
                                progressDialog.dismiss();
                                User user = dataSnapshot.child(userPhone).getValue(User.class);
                                assert user != null;
                                if (user.getUserpassword().equals(userpassword)) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
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

    private void login(final String userPhone, final String pwd) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.show();
        pd.setContentView(R.layout.progress_layout);
        pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userPhone).exists()) {
                    pd.dismiss();
                    User user = dataSnapshot.child(userPhone).getValue(User.class);
                    assert user != null;
                    if (user.getUserpassword().equals(pwd)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
