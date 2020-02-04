package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.TryModel;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.FETCH_ROUTE;
import static com.atandi.smarttravel.Constants.Links.REGISTER_VEHICLE;

public class VehicleRegistrationFragment extends Fragment {
    Context context;
    LayoutInflater inflater;

    EditText VRVehicleNumber, VRVOwnerName, VRVOwnerNumber, VRVSeats;
    Spinner VRVSpinnerId;
    ImageView VRVpicId;
    Button BtnVRegister;
    List<String> mRoute;
    TextView selctedroute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vehicle_register, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VRVehicleNumber = view.findViewById(R.id.VRVehicleNumber);
        VRVSpinnerId = view.findViewById(R.id.VRVSpinnerId);
        VRVOwnerName = view.findViewById(R.id.VRVOwnerName);
        VRVOwnerNumber = view.findViewById(R.id.VRVOwnerNumber);
        VRVSeats = view.findViewById(R.id.VRVSeats);
        VRVpicId = view.findViewById(R.id.VRVpicId);
        BtnVRegister = view.findViewById(R.id.BtnVRegister);
        mRoute = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mRoute);
        VRVSpinnerId.setAdapter(adapter);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_ROUTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String rn = jsonObject.getString("routename");
                        mRoute.add(rn);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);



        SpinnerListener(VRVSpinnerId);


        BtnVRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterVehicle();
            }
        });

    }

    private void SpinnerListener(Spinner spinner) {
        VRVSpinnerId.setOnItemSelectedListener(new mySelectedListener());
    }


    private void RegisterVehicle() {
        final String plates = VRVehicleNumber.getText().toString();
        final String owner = VRVOwnerName.getText().toString();
        final String phones = VRVOwnerNumber.getText().toString();
        final String seats = VRVSeats.getText().toString();
        final String route = VRVSpinnerId.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_VEHICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle("Smart Travel");
                builder.setMessage("Vehicle has been registered");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VRVehicleNumber.setText("");
                        VRVOwnerName.setText("");
                        VRVOwnerNumber.setText("");
                        VRVpicId.setImageResource(R.drawable.ic_car);
                        VRVSeats.setText("");
                    }
                });
                builder.create().show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> vMap = new HashMap<>();
                vMap.put("vehicle_plate", plates);
                vMap.put("vehicle_owner", owner);
                vMap.put("owner_phone", phones);
                vMap.put("vehicle_number_of_seats", seats);
                vMap.put("routeName", route);
                return vMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public class mySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
