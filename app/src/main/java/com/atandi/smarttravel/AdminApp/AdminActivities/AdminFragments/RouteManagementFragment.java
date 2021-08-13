package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.Activities.RegisterActivity;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.ADD_PICK_DETAILS;
import static com.atandi.smarttravel.Constants.Links.ADD_ROUTE_DETAILS;
import static com.atandi.smarttravel.Constants.Links.FETCH_PLATES;
import static com.atandi.smarttravel.Constants.Links.FETCH_ROUTE;
import static com.atandi.smarttravel.Constants.Links.UPDATE_ROUTE_DETAILS;

public class RouteManagementFragment extends Fragment {

    private CardView NewAddRoute,manage,NewAddPick;

    private EditText setVehicleRemainingSeats,newRouteName,newRouteCost,newPickName;
    private Spinner bookStatusCheck,setVehicleSpinner,routeSpinner,pickRouteName;

    private ProgressDialog progressDialog;

    private TextView newRoute,newPickPoint;
    private Button BtnManage,BtnUpdateRoute,BtnAddRoute,BtnAddPick;

    private String[] status = {"not booking","booking"};
    private List<String> vehicles,routes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.route_managemnt,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        progressDialog = new ProgressDialog(getContext());


//       spinners
        routeSpinner = view.findViewById(R.id.routeSpinner);
        setVehicleSpinner = view.findViewById(R.id.setVehicleSpinner);
        bookStatusCheck = view.findViewById(R.id.bookStatusCheck);
        pickRouteName = view.findViewById(R.id.pickRouteName);

//        EditText
        setVehicleRemainingSeats = view.findViewById(R.id.setVehicleRemainingSeats);
        newRouteName = view.findViewById(R.id.newRouteName);
        newRouteCost = view.findViewById(R.id.newRouteCost);
        newPickName = view.findViewById(R.id.newPickName);

//        TextView
        newRoute = view.findViewById(R.id.newRoute);
        newPickPoint = view.findViewById(R.id.newPickPoint);

//           Cards
        NewAddRoute = view.findViewById(R.id.NewAddRoute);
        NewAddPick = view.findViewById(R.id.NewAddPick);
        manage = view.findViewById(R.id.manage);

//        Buttons
        BtnManage = view.findViewById(R.id.BtnManage);
        BtnUpdateRoute = view.findViewById(R.id.BtnUpdateRoute);
        BtnAddRoute = view.findViewById(R.id.BtnAddRoute);
        BtnAddPick  = view.findViewById(R.id.BtnAddPick);

        routes = new ArrayList<>();
        vehicles = new ArrayList<>();

//        for booking status
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,status);
        bookStatusCheck.setAdapter(statusAdapter);

//        for routes spinner
       final ArrayAdapter<String> routesAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,routes);
        routeSpinner.setAdapter(routesAdapter);
        pickRouteName.setAdapter(routesAdapter);



        final StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_ROUTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String rn = jsonObject.getString("routename");
                        routes.add(rn);
                    }
                    routesAdapter.notifyDataSetChanged();

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
        requestQueue.add(stringRequest);

//        for vehicles spinner
        final ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,vehicles);
        setVehicleSpinner.setAdapter(vehicleAdapter);

        StringRequest fstring = new StringRequest(Request.Method.GET, FETCH_PLATES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        String plates =jsonObject.getString("vehicle_plate");
                        vehicles.add(plates);
                    }
                    vehicleAdapter.notifyDataSetChanged();

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
        requestQueue.add(fstring);

//      manage the buttons controls for managing routes

        BtnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manage.getVisibility()==View.GONE){
                    manage.setVisibility(View.VISIBLE);
                    NewAddRoute.setVisibility(View.GONE);
                    NewAddPick.setVisibility(View.GONE);
                }
                else{
                    if(manage.getVisibility()==View.VISIBLE){
                        manage.setVisibility(View.GONE);
                    }
                }
            }
        });

//        for adding new route

        newRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewAddRoute.getVisibility()==View.GONE){
                    NewAddRoute.setVisibility(View.VISIBLE);
                    manage.setVisibility(View.GONE);
                }
                else {
                    if(NewAddRoute.getVisibility()==View.VISIBLE){
                        NewAddRoute.setVisibility(View.GONE);
                    }

                }
            }
        });

        //        for adding new pick point

        newPickPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewAddPick.getVisibility()==View.GONE){
                    NewAddPick.setVisibility(View.VISIBLE);
                    manage.setVisibility(View.GONE);
                    NewAddRoute.setVisibility(View.GONE);
                }
                else {
                    if(newPickPoint.getVisibility()==View.VISIBLE){
                        NewAddPick.setVisibility(View.GONE);
                    }

                }
            }
        });

//   Submit route updates
        BtnUpdateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                if (routeSpinner.getSelectedItem() == null || bookStatusCheck.getSelectedItem() == null || setVehicleSpinner.getSelectedItem() == null ||
                        setVehicleRemainingSeats.getText() == null) {
                    progressDialog.dismiss();
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(getContext(),"All fields are required,,please ensure you have access to the sever");

                } else {
                    final String uRouteName = routeSpinner.getSelectedItem().toString();
                    final String uStatus = bookStatusCheck.getSelectedItem().toString();
                    final String uVehicle = setVehicleSpinner.getSelectedItem().toString();
                    final String uSeatsRemaining = setVehicleRemainingSeats.getText().toString();

                    StringRequest updates = new StringRequest(Request.Method.POST, UPDATE_ROUTE_DETAILS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Smart Travel");
                            alert.setMessage(response);
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            manage.setVisibility(View.GONE);
                                        }
                                    }
                            );
                            alert.show();

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> updMap = new HashMap<>();
                            updMap.put("route_name", uRouteName);
                            updMap.put("status", uStatus);
                            updMap.put("vehicle", uVehicle);
                            updMap.put("seats", uSeatsRemaining);
                            return updMap;
                        }
                    };
                    requestQueue.add(updates);
                }
            }
        });


//        add new route

        BtnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                final String routenames = newRouteName.getText().toString();
                final String routecost = newRouteCost.getText().toString();


                if (routenames.isEmpty() || routecost.isEmpty()) {
                    progressDialog.dismiss();
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(getContext(),"All fields are required");

                } else {
                    StringRequest addroute = new StringRequest(Request.Method.POST, ADD_ROUTE_DETAILS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Smart Travel");
                            alert.setMessage(response);
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            newRouteName.setText("");
                                            newRouteCost.setText("");
                                            NewAddRoute.setVisibility(View.GONE);
                                        }
                                    }
                            );
                            alert.show();

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> newRouteMap = new HashMap<>();
                            newRouteMap.put("routes_name", routenames);
                            newRouteMap.put("route_cost", routecost);
                            return newRouteMap;
                        }
                    };
                    requestQueue.add(addroute);
                }
            }
        });



        BtnAddPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if (pickRouteName.getSelectedItem().toString().isEmpty()) {
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(getContext(),"sorry you dont have connection to our servers at the moment");

                } else {
                    final String routename = pickRouteName.getSelectedItem().toString();
                    final String pickname = newPickName.getText().toString();


                    if (routename.isEmpty() || pickname.isEmpty()) {
                        progressDialog.dismiss();
                        MyBuilderClass myBuilderClass = new MyBuilderClass();
                        myBuilderClass.MyBuilder(getContext(), "All fields are required");

                    } else {
                        StringRequest addpickpoint = new StringRequest(Request.Method.POST, ADD_PICK_DETAILS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Smart Travel");
                                alert.setMessage(response);
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                newPickName.setText("");
                                                NewAddPick.setVisibility(View.GONE);
                                            }
                                        }
                                );
                                alert.show();

                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> newRouteMap = new HashMap<>();
                                newRouteMap.put("route_name", routename);
                                newRouteMap.put("pick_point_name", pickname);
                                return newRouteMap;
                            }
                        };
                        requestQueue.add(addpickpoint);
                    }
                }
            }
        });


    }
}
