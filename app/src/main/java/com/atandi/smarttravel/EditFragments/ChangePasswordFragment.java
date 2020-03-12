package com.atandi.smarttravel.EditFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atandi.smarttravel.Models.User;
import com.atandi.smarttravel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;

public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    EditText currentPassword,newPassword,confirmPassword;
    Button btnChangePasswordCancel,btnChangePassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(Objects.requireNonNull(getContext()));

        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnChangePasswordCancel = view.findViewById(R.id.btnChangePasswordCancel);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        btnChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = Paper.book().read(USER_PHONE);
                final String current = currentPassword.getText().toString();
                final String newp = newPassword.getText().toString();
                final String confirm = confirmPassword.getText().toString();

                if (phone.isEmpty() || newp.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.child(phone).getValue(User.class);

                            assert user != null;
                            if (user.getUserpassword().equals(current)) {
                                if (newp.equals(confirm)) {
                                    reference.child(phone).child("userpassword").setValue(newp);
                                    currentPassword.setText("");
                                    newPassword.setText("");
                                    confirmPassword.setText("");
                                    Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Passwords not matching", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Your current password is incorrect", Toast.LENGTH_SHORT).show();
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
