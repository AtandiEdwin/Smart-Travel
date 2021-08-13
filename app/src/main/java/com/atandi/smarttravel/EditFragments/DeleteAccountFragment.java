package com.atandi.smarttravel.EditFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.Links.DELETE_ACCOUNT;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;
import static java.util.Objects.*;

public class DeleteAccountFragment extends Fragment {
    public DeleteAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_account, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireNonNull(getContext()));
        Button btnDeleteAccount  =view.findViewById(R.id.btnDeleteAccount);

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Smart Travel");
                alert.setMessage("Are you sure you want to delete your account");
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requireNonNull(getActivity()).onBackPressed();
                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       final String delete_phone =  Paper.book().read(USER_PHONE);

                       if(delete_phone==null){
                           Toast.makeText(getContext(), "phone number is required", Toast.LENGTH_SHORT).show();
                       }
                       else{

                           StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ACCOUNT, new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                   DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                   Toast.makeText(getContext(), "Account deleting "+response, Toast.LENGTH_SHORT).show();
                               }
                           },
                                   new Response.ErrorListener() {
                                       @Override
                                       public void onErrorResponse(VolleyError error) {
                                       }
                                   }){
                               @Override
                               protected Map<String, String> getParams(){
                                   Map<String,String> deleteMap = new HashMap<>();
                                   deleteMap.put("phone",delete_phone);
                                   return deleteMap;
                               }
                           };
                           RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                           requestQueue.add(stringRequest);
                       }
                    }
                });
                alert.create().show();
            }
        });
    }
}
