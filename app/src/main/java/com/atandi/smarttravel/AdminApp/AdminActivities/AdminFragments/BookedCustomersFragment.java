package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminAdapters.PendingUserAdapter;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.PendingUserModel;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.FETCH_PENDING_USERS;
import static com.atandi.smarttravel.Constants.Links.FETCH_PLATES;
import static com.atandi.smarttravel.Constants.Links.FETCH_ROUTE;

public class BookedCustomersFragment extends Fragment {
    Context context;
    LayoutInflater inflater;
    Spinner pendingUserSpinner,pendingUserRouteSpinner;
    Button BtnLoadUsers;
    RecyclerView pendingUserRecycler;
    List<String> vehiclelist,routelist;
    List<PendingUserModel> mUser;
    ProgressDialog progressDialog;
    TextView noCustomer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pending_user,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pendingUserSpinner = view.findViewById(R.id.pendingUserSpinner);
        pendingUserRouteSpinner = view.findViewById(R.id.pendingUserRouteSpinner);
        noCustomer = view.findViewById(R.id.noCustomer);
        BtnLoadUsers = view.findViewById(R.id.BtnLoadUsers);
        pendingUserRecycler = view.findViewById(R.id.pendingUserRecycler);
        pendingUserRecycler.setHasFixedSize(true);
        pendingUserRecycler.setNestedScrollingEnabled(false);

        vehiclelist = new ArrayList<>();
        routelist = new ArrayList<>();
        mUser = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, vehiclelist);
        pendingUserSpinner.setAdapter(adapter);

        StringRequest fstring = new StringRequest(Request.Method.GET, FETCH_PLATES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        String plates =jsonObject.getString("vehicle_plate");
                        vehiclelist.add(plates);
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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(fstring);

        final ArrayAdapter<String> routeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, routelist);
        pendingUserRouteSpinner.setAdapter(routeAdapter);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_ROUTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String rn = jsonObject.getString("routename");
                        routelist.add(rn);
                    }
                    routeAdapter.notifyDataSetChanged();

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

        RequestQueue mrequestQueue = Volley.newRequestQueue(getContext());
        mrequestQueue.add(stringRequest);

        BtnLoadUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pendingUserRecycler.getVisibility()==View.GONE && noCustomer.getVisibility()==View.GONE){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_layout);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    loadUsers();
                }
                else if(pendingUserRecycler.getVisibility()==View.VISIBLE || noCustomer.getVisibility()==View.VISIBLE){
                    pendingUserRecycler.setVisibility(View.GONE);
                    noCustomer.setVisibility(View.GONE);
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_layout);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    loadUsers();
                }
                else {
                    pendingUserRecycler.setVisibility(View.GONE);
                    noCustomer.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadUsers() {
        if(pendingUserSpinner.getSelectedItem()==null || pendingUserRouteSpinner.getSelectedItem()==null ){
            MyBuilderClass myBuilderClass = new MyBuilderClass();
            myBuilderClass.MyBuilder(getContext(),"sorry seems you don't have access to the server.Please consult the administrators");
            progressDialog.dismiss();
        }
        else{
            final String plate_number = pendingUserSpinner.getSelectedItem().toString();
            final  String routename = pendingUserRouteSpinner.getSelectedItem().toString();
            final StringRequest pstringRequest = new StringRequest(Request.Method.POST, FETCH_PENDING_USERS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                        JSONArray jsonArray;
                        mUser.clear();
                        try {
                            jsonArray = new JSONArray(response);
                            if (jsonArray.length()==0) {
                                progressDialog.dismiss();
                                pendingUserRecycler.setVisibility(View.GONE);
                                noCustomer.setVisibility(View.VISIBLE);
                            } else {
                                progressDialog.dismiss();
                                noCustomer.setVisibility(View.GONE);
                                pendingUserRecycler.setVisibility(View.VISIBLE);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String phones = jsonObject.getString("user_phone");
                                    String pickpoint = jsonObject.getString("pickpoint");
                                    PendingUserModel pendingUserModel = new PendingUserModel(phones, pickpoint);
                                    mUser.add(pendingUserModel);
                                }
                                PendingUserAdapter adapter = new PendingUserAdapter(getContext(), mUser);
                                pendingUserRecycler.setAdapter(adapter);
                                pendingUserRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    }
            ){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> map = new HashMap<>();
                    map.put("routename",routename);
                    map.put("plate_number",plate_number);
                    return map;
                }
            };

            RequestQueue mrequestQueue = Volley.newRequestQueue(getContext());
            mrequestQueue.add(pstringRequest);
        }


    }
}
