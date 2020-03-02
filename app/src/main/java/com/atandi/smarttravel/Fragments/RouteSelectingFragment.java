package com.atandi.smarttravel.Fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import com.atandi.smarttravel.Adapters.PickPointSelectAdapter;
import com.atandi.smarttravel.Adapters.RouteSelectAdapter;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.Constants.MySingleton;
import com.atandi.smarttravel.Models.DetailsViewModel;
import com.atandi.smarttravel.Models.PickPointSelectModel;
import com.atandi.smarttravel.Models.RouteSelectModel;
import com.atandi.smarttravel.Models.RouteViewModel;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.FETCH_DETAILS;
import static com.atandi.smarttravel.Constants.Links.FETCH_PICKPOINT;
import static com.atandi.smarttravel.Constants.Links.FETCH_ROUTE;

public class RouteSelectingFragment extends Fragment {
    public RouteSelectingFragment() {
    }

    EditText RouteEdit,pick;
    private RouteViewModel routeViewModel;
    private DetailsViewModel model;
    Button BtnNext;
    RecyclerView routeRecycler,pickRecyclerId;
    List<RouteSelectModel> mroute;
    List<PickPointSelectModel> mpick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_selecting_layout,null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        model.getmlist().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {

            }
        });

       routeViewModel = ViewModelProviders.of(getActivity()).get(RouteViewModel.class);
        routeViewModel.getRoutepick().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mroute = new ArrayList<>();
        mpick = new ArrayList<>();

        Date d = new Date();
        final String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dates = simpleDateFormat.format(d);


        TextView dateTextView = view.findViewById(R.id.date);
        dateTextView.setText(dates);

        pick = view.findViewById(R.id.pick);
        pickRecyclerId = view.findViewById(R.id.pickRecyclerId);

        BtnNext= view.findViewById(R.id.BtnNext);
        RouteEdit= view.findViewById(R.id.EditRoute);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,new IntentFilter("custom-message"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver1,new IntentFilter("custom-message1"));

        routeRecycler= view.findViewById(R.id.routeRecyclerId);
        routeRecycler.setHasFixedSize(true);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_ROUTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mroute.clear();

                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject =jsonArray.getJSONObject(i);

                        String rn =jsonObject.getString("routename");

                        RouteSelectModel routeSelectModel = new RouteSelectModel(rn);
                        mroute.add(routeSelectModel);
                    }

                    RouteSelectAdapter adapter = new RouteSelectAdapter(getContext(),mroute);
                    routeRecycler.setAdapter(adapter);
                    routeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));




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


        RouteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(routeRecycler.getVisibility()==View.GONE){
                    routeRecycler.setVisibility(View.VISIBLE);
                    BtnNext.setVisibility(View.GONE);
                }
                else if(BtnNext.getVisibility()==View.GONE){
                    routeRecycler.setVisibility(View.GONE);
//                    BtnNext.setVisibility(View.VISIBLE);
                }
            }
        });


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RouteEdit.getText().toString().isEmpty()){
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(getContext(),"Please select your route first");
                }
                else{
                    final String userRoute  = RouteEdit.getText().toString();
                    final StringRequest stringRequestpickpoint = new StringRequest(Request.Method.POST, FETCH_PICKPOINT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mpick.clear();

                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);

                                for(int i=0; i<jsonArray.length();i++){
                                    JSONObject jsonObject =jsonArray.getJSONObject(i);

                                    String pn =jsonObject.getString("pick_point");

                                    PickPointSelectModel pickPointSelectModel = new PickPointSelectModel(pn);
                                    mpick.add(pickPointSelectModel);
                                }

                                PickPointSelectAdapter pickPointSelectAdapter = new PickPointSelectAdapter(getContext(),mpick);
                                pickRecyclerId.setAdapter(pickPointSelectAdapter);
                                pickRecyclerId.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    ){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String,String> pmap  = new HashMap<>();
                            pmap.put("route",userRoute);
                            return pmap;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequestpickpoint);
                    if(pickRecyclerId.getVisibility()==View.GONE){
                        pickRecyclerId.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog  progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                final String ROUTE =RouteEdit.getText().toString();
                final String PICKPOINT = pick.getText().toString();

                if(ROUTE.isEmpty()){
                    progressDialog.dismiss();
                    AlertDialog.Builder alert  = new AlertDialog.Builder(getContext());
                    alert.setTitle("Smart Travel");
                    alert.setMessage("Please select the route you are travelling");
                    alert.setCancelable(true);
                    alert.show();
                }
                else{
                    List routepick = new ArrayList();
                    routepick.add(ROUTE);
                    routepick.add(PICKPOINT);
                    routeViewModel.setRoutepick(routepick);

                    StringRequest myrequest = new StringRequest(Request.Method.POST, FETCH_DETAILS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                                JSONArray jsonArray ;
                                try {
                                    jsonArray = new JSONArray(response);

                                    if(jsonArray.length()==0){
                                        progressDialog.dismiss();
                                        AlertDialog.Builder alert  = new AlertDialog.Builder(getContext());
                                        alert.setTitle("Smart Travel");
                                        alert.setMessage("we are sorry none of our vehicles are booking to the selected route at the moment");
                                        alert.setCancelable(true);
                                        alert.show();
                                    }
                                    else{
                                        progressDialog.dismiss();
                                    for(int i= 0; i<jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String vehicleplate = jsonObject.getString("vehicle_plate");
                                        String vehiclepic = jsonObject.getString("vehicle_pic");
                                        String vehicleseats = jsonObject.getString("vehicle_number_of_seats");
                                        String seatsremaining = jsonObject.getString("seats_remaining");
                                        String cost = jsonObject.getString("cost");
                                        String drivername = jsonObject.getString("driver_name");
                                        String drivernumber = jsonObject.getString("driver_number");
                                        String driverpic = jsonObject.getString("driver_pic");

                                        List mlist = new ArrayList();
                                        mlist.add(vehicleplate);
                                        mlist.add(vehiclepic);
                                        mlist.add(vehicleseats);
                                        mlist.add(seatsremaining);
                                        mlist.add(cost);
                                        mlist.add(drivername);
                                        mlist.add(drivernumber);
                                        mlist.add(driverpic);

                                        model.setMlist(mlist);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_fragment, new BookingFragment());
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                        progressDialog.dismiss();
                                    }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    AlertDialog.Builder alert  = new AlertDialog.Builder(getContext());
                                    alert.setTitle("Smart Travel");
                                    alert.setMessage("sorry!! our servers are down at the moment please try again later");
                                    alert.setCancelable(true);
                                    alert.show();
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
                            Map<String,String> mMap = new HashMap<>();
                            mMap.put("routename",ROUTE);
                            return mMap;
                        }
                    };
                    RequestQueue request = Volley.newRequestQueue(getContext());
                    request.add(myrequest);
                }
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String finalRouteName = intent.getStringExtra("RouteName");
            String finalPickName = intent.getStringExtra("PickName");
            RouteEdit.setText(finalRouteName);
            routeRecycler.setVisibility(View.GONE);
            pick.setText(finalPickName);
            pickRecyclerId.setVisibility(View.GONE);
        }
    };

    public BroadcastReceiver mMessageReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String finalPickName = intent.getStringExtra("PickName");
            pick.setText(finalPickName);
            pickRecyclerId.setVisibility(View.GONE);
            BtnNext.setVisibility(View.VISIBLE);
        }
    };
}
