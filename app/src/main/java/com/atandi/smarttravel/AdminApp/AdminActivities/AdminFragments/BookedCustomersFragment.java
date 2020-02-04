package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import static com.atandi.smarttravel.Constants.Links.FETCH_PLATES;
import static com.atandi.smarttravel.Constants.Links.FETCH_ROUTE;

public class BookedCustomersFragment extends Fragment {
    Context context;
    LayoutInflater inflater;
    Spinner pendingUserSpinner,pendingUserRouteSpinner;
    Button BtnLoadUsers;
    RecyclerView pendingUserRecycler;
    List<String> vehiclelist,routelist;

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
        BtnLoadUsers = view.findViewById(R.id.BtnLoadUsers);
        pendingUserRecycler = view.findViewById(R.id.pendingUserRecycler);
        pendingUserRecycler.setHasFixedSize(true);

        vehiclelist = new ArrayList<>();
        routelist = new ArrayList<>();

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






    }
}
