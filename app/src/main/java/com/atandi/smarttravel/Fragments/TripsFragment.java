package com.atandi.smarttravel.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.atandi.smarttravel.Adapters.TripsAdapter;
import com.atandi.smarttravel.Models.TripsModel;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.Links.FETCH_TRIPS;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;

public class TripsFragment extends Fragment{

    TextView toDates,fromDates ,dismissTxt,noTrip;
    Button btnload;
    RecyclerView tripRecycler;
   List<TripsModel> myTrips;
   ProgressDialog progressDialog;

    public TripsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trips_layout,null);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myTrips = new ArrayList<>();
        progressDialog =  new ProgressDialog(getContext());

//        paper stuff
        Paper.init(Objects.requireNonNull(getContext()));




        ImageView fromDate = view.findViewById(R.id.fromDateCalender);
        ImageView toDate = view.findViewById(R.id.toDateCalender);

        dismissTxt= view.findViewById(R.id.dismissTxt);
        noTrip = view.findViewById(R.id.noTrip);

        toDates = view.findViewById(R.id.toDate);
        fromDates = view.findViewById(R.id.fromDate);

        btnload  = view.findViewById(R.id.BtnLoad);
        tripRecycler = view.findViewById(R.id.tripsRecyclerId);
        tripRecycler.setHasFixedSize(true);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from","from");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getFragmentManager(),"from");

                LocalBroadcastManager.getInstance(getContext()).registerReceiver(frommReceiver, new IntentFilter("from-date"));
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from","to");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getFragmentManager(),"to");

                LocalBroadcastManager.getInstance(getContext()).registerReceiver(toReceiver, new IntentFilter("to-date"));
            }
        });


        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    if(tripRecycler.getVisibility()==View.GONE && noTrip.getVisibility()==View.GONE){
                        dismissTxt.setVisibility(View.VISIBLE);
                        loadTrips();
                    }
                    else if(tripRecycler.getVisibility()==View.VISIBLE || noTrip.getVisibility()==View.VISIBLE) {
                        dismissTxt.setVisibility(View.GONE);
                        loadTrips();
                    }
                    else{
                        progressDialog.dismiss();
                        noTrip.setVisibility(View.GONE);
                        tripRecycler.setVisibility(View.GONE);
                        dismissTxt.setVisibility(View.VISIBLE);
                    }
                }
        });

}

private void loadTrips(){

    final String FROMDATE = fromDates.getText().toString();
    final String TODATE = toDates.getText().toString();
//    final String phone = Paper.book().read(USER_PHONE);
    final String phone ="0726123366";

    if(FROMDATE.isEmpty() && TODATE.isEmpty()){
        progressDialog.dismiss();
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Smart Travel");
        alert.setMessage("Please select the dates");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    tripRecycler.setVisibility(View.GONE);
                    noTrip.setVisibility(View.GONE);
                    dismissTxt.setVisibility(View.VISIBLE);
            }
        });
        alert.show();
    }
    else{
        if(phone==null){
            progressDialog.dismiss();
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Smart Travel");
            alert.setMessage("Please login in seems you are not logged in");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tripRecycler.setVisibility(View.GONE);
                    noTrip.setVisibility(View.GONE);
                    dismissTxt.setVisibility(View.VISIBLE);
                }
            });
            alert.show();
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_TRIPS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            dismissTxt.setVisibility(View.GONE);
                            tripRecycler.setVisibility(View.GONE);
                            noTrip.setVisibility(View.VISIBLE);
                        } else {
                            progressDialog.dismiss();
                            dismissTxt.setVisibility(View.GONE);
                            noTrip.setVisibility(View.GONE);
                            tripRecycler.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String routeName = jsonObject.getString("route_name");
                                String vehiclePlate = jsonObject.getString("vehicle_plate");
                                String tripCost = jsonObject.getString("trip_cost");
                                String tripDate = jsonObject.getString("trip_date");
                                String seatsBooked = jsonObject.getString("seatsBooked");
                                String tripstatus = jsonObject.getString("status");

                                TripsModel model = new TripsModel(routeName, vehiclePlate, tripCost, tripDate, seatsBooked, tripstatus);
                                myTrips.add(model);

                            }
                            TripsAdapter adapter = new TripsAdapter(getContext(), myTrips);
                            tripRecycler.setAdapter(adapter);
                            tripRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> tripMap = new HashMap<>();
                    tripMap.put("from_date", FROMDATE);
                    tripMap.put("to_date", TODATE);
                    tripMap.put("phone", phone);
                    return tripMap;
                }
            };
            RequestQueue newRequestQueue = Volley.newRequestQueue(getContext());
            newRequestQueue.add(stringRequest);
        }
    }

}

    BroadcastReceiver frommReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fromDates.setText(intent.getStringExtra("fromdate"));
        }
    };

    BroadcastReceiver toReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            toDates.setText(intent.getStringExtra("todate"));
        }
    };


}
