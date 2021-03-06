package com.atandi.smarttravel.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import static com.atandi.smarttravel.Constants.Links.FETCH_TRIPS;

public class TripsFragment extends Fragment{

    TextView toDates,fromDates ,dismissTxt;
    Button btnload;
    RecyclerView tripRecycler;
   List<TripsModel> myTrips;

    public TripsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trips_layout,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myTrips = new ArrayList<>();

        ImageView fromDate = view.findViewById(R.id.fromDateCalender);
        ImageView toDate = view.findViewById(R.id.toDateCalender);

        dismissTxt= view.findViewById(R.id.dismissTxt);

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

                if(dismissTxt.getVisibility()==View.VISIBLE){
                    dismissTxt.setVisibility(View.GONE);
                    if(tripRecycler.getVisibility()==View.GONE){
                        tripRecycler.setVisibility(View.VISIBLE);

                        final String FROMDATE = fromDates.getText().toString();
                        final String TODATE = toDates.getText().toString();

                        if(FROMDATE.isEmpty() && TODATE.isEmpty()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Smart Travel");
                            alert.setMessage("Please select the dates");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(tripRecycler.getVisibility()==View.VISIBLE){
                                        tripRecycler.setVisibility(View.GONE);
                                        dismissTxt.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            alert.show();
                        }
                        else{
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_TRIPS, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);

                                        for(int i = 0; i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String routeName = jsonObject.getString("route_name");
                                            String vehiclePlate =jsonObject.getString("vehicle_plate");
                                            String tripCost =jsonObject.getString("trip_cost");
                                            String tripDate =jsonObject.getString("trip_date");
                                            String seatsBooked = jsonObject.getString("seatsBooked");

                                            TripsModel model = new TripsModel(routeName,vehiclePlate,tripCost,tripDate,seatsBooked);
                                            myTrips.add(model);

                                        }
                                        TripsAdapter adapter = new TripsAdapter(getContext(),myTrips);
                                        tripRecycler.setAdapter(adapter);
                                        tripRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
                                    Map<String,String> tripMap = new HashMap<>();
                                    tripMap.put("from_date",FROMDATE);
                                    tripMap.put("to_date",TODATE);
                                    return tripMap;
                                }
                            };
                            RequestQueue newRequestQueue = Volley.newRequestQueue(getContext());
                            newRequestQueue.add(stringRequest);
                        }
                    }
                    else{
                        if(tripRecycler.getVisibility()==View.VISIBLE){
                            tripRecycler.setVisibility(View.GONE);
                            dismissTxt.setVisibility(View.VISIBLE);
                        }
                    }
                }



            }
        });

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
