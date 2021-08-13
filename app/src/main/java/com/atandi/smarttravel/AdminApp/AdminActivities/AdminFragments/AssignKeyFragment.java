package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

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
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.R;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.atandi.smarttravel.Constants.Links.ASSIGNKEY;

public class AssignKeyFragment extends Fragment {

    public AssignKeyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_key, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText keyPhoneId = view.findViewById(R.id.keyPhoneId);
        final EditText keyId = view.findViewById(R.id.keyId);
        final EditText keyIdPass = view.findViewById(R.id.keyIdPass);
        Button btn_Assign = view.findViewById(R.id.btn_Assign);

        btn_Assign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final String customer_phone = keyPhoneId.getText().toString();
                final String user_key = keyId.getText().toString();
                final String user_id = keyIdPass.getText().toString();

                if (customer_phone.isEmpty()) {
                    keyPhoneId.setError("phone number is required");
                } else {
                    if (user_key.isEmpty()) {
                        keyId.setError("please assign a key");
                    } else {
                        if (user_id.isEmpty()) {
                            keyIdPass.setError("please provide the user identification number");
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ASSIGNKEY, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    alert.setTitle(getString(R.string.app_name));
                                    alert.setMessage(response);
                                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            keyPhoneId.setText("");
                                            keyId.setText("");
                                            keyIdPass.setText("");
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.create().show();
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();

                                    map.put("user_phone", customer_phone);
                                    map.put("user_key", user_key);
                                    map.put("user_id",user_id);
                                    return map;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                            requestQueue.add(stringRequest);
                        }
                    }
                }
            }
        });



    }
}
