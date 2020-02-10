package com.atandi.smarttravel.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.Models.RouteViewModel;
import com.atandi.smarttravel.Models.SummaryViewModel;
import com.atandi.smarttravel.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.SAVE_DETAILS;

public class SummaryFragment extends Fragment {

    private SummaryViewModel summaryViewModel;
    private RouteViewModel routeViewModel;

    TextView endRouteId,endPickId,endSeatsId,endCostId,endVehicleId,endPhoneId;

    public SummaryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booking_summary_layout,null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        summaryViewModel = ViewModelProviders.of(getActivity()).get(SummaryViewModel.class);
        summaryViewModel.getSummary().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                endVehicleId.setText(list.get(1).toString());
                endSeatsId.setText(list.get(2).toString());
                endCostId.setText(list.get(0).toString());
                endPhoneId.setText(list.get(3).toString());
            }
        });

       routeViewModel = ViewModelProviders.of(getActivity()).get(RouteViewModel.class);
        routeViewModel.getRoutepick().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                endRouteId.setText(list.get(0).toString());
                endPickId.setText(list.get(1).toString());
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        endCostId = view.findViewById(R.id.endCostId);
        endPickId = view.findViewById(R.id.endPickId);
        endRouteId = view.findViewById(R.id.endRouteId);
        endSeatsId = view.findViewById(R.id.endSeatsId);
        endVehicleId = view.findViewById(R.id.endVehicleId);
        endPhoneId = view.findViewById(R.id.endPhoneId);

        Button BtnBack = view.findViewById(R.id.BtnBack);
        Button BtnOk = view.findViewById(R.id.BtnOk);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        BtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String route = endRouteId.getText().toString();
                final String pickpoint = endPickId.getText().toString();
                final String seats_booked = endSeatsId.getText().toString();
                final String totalcost = endCostId.getText().toString();
                final String vehicle_plate = endVehicleId.getText().toString();
                final String user_phone = endPhoneId.getText().toString();

                StringRequest newStringRequest = new StringRequest(Request.Method.POST, SAVE_DETAILS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                       alert.setTitle("Smart Travel");
                       alert.setMessage(response);
                       alert.setCancelable(false);
                       alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               endCostId.setText("");
                               endPickId.setText("");
                               endRouteId.setText("");
                               endSeatsId.setText("");
                               endVehicleId.setText("");
                               getActivity().onBackPressed();
                           }
                       }
                       );
                       alert.show();

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "error" +error, Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> myMap = new HashMap<>();

                        myMap.put("route",route);
                        myMap.put("pickpoint",pickpoint);
                        myMap.put("seats_booked",seats_booked);
                        myMap.put("totalcost",totalcost);
                        myMap.put("vehicle_plate",vehicle_plate);
                        myMap.put("user_phone",user_phone);

                        return myMap;
                    }
                };

                RequestQueue newRequestQueue = Volley.newRequestQueue(getContext());
                newRequestQueue.add(newStringRequest);
            }
        });
    }
}
