package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.FETCH_PLATES;
import static com.atandi.smarttravel.Constants.Links.REGISTER_DRIVER;

public class DriverRegistrationFragment extends Fragment {
    Context context;
    LayoutInflater inflater;
    ImageView DriverRPic;
    Spinner DRSpinnerId;
    EditText DRName,DRNumber;
    List<String> mPlates;
    Button BtnDRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.driver_register,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DRName = view.findViewById(R.id.DRName);
        DRSpinnerId = view.findViewById(R.id.DRSpinnerId);
        DriverRPic = view.findViewById(R.id.DriverRPic);
        DRNumber =view.findViewById(R.id.DRNumber);
        BtnDRegister = view.findViewById(R.id.BtnDRegister);

        mPlates = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,mPlates);
        DRSpinnerId.setAdapter(adapter);

        fetchPlates();

        BtnDRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeradriver();
            }
        });


    }

    private void fetchPlates() {
        StringRequest fstring = new StringRequest(Request.Method.GET, FETCH_PLATES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "i" + response, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        String rn =jsonObject.getString("routename");
                        mPlates.add(rn);
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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(fstring);

    }

    private void Registeradriver() {
        final String drnames = DRName.getText().toString();
        final String drnumber = DRNumber.getText().toString();
//        final String drpic = DriverRPic.getText().toString();
        final String drVehicle = DRSpinnerId.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_DRIVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "okey"+ response, Toast.LENGTH_SHORT).show();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "okey"+ error, Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> DRMAP = new HashMap<>();
                DRMAP.put("driver_name",drnames);
                DRMAP.put("driver_number",drnumber);
                DRMAP.put("vehicle_plate",drVehicle);
                return DRMAP;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
