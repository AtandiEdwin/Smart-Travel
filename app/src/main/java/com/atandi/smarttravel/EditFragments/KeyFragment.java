package com.atandi.smarttravel.EditFragments;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.Activities.MapsActivity;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.atandi.smarttravel.Constants.Links.CHECKKEY;

public class KeyFragment extends Fragment {

    public KeyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_key, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_keyMap = view.findViewById(R.id.btn_keyMap);
        final EditText keyId = view.findViewById(R.id.keyId);

        btn_keyMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final String key = keyId.getText().toString();
                if(key.isEmpty()){
                    Toast.makeText(getContext(), "key cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECKKEY, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String plate= jsonObject.getString("vehicle_plate");

                                    Intent intent = new Intent(getContext(),MapsActivity.class);
                                    intent.putExtra("plate",plate);
                                    startActivity(intent);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("user_key",key);
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                    requestQueue.add(stringRequest);

                }
            }
        });

    }
}
