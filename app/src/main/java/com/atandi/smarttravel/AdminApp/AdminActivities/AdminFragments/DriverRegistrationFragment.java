package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.atandi.smarttravel.Constants.MyBuilderClass;
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

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,mPlates);
        DRSpinnerId.setAdapter(adapter);

        StringRequest fstring = new StringRequest(Request.Method.GET, FETCH_PLATES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        String plates =jsonObject.getString("vehicle_plate");
                        mPlates.add(plates);
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



        BtnDRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeradriver();
            }
        });


    }

    private void Registeradriver() {
        if(DRSpinnerId.getSelectedItem()==null ){
            MyBuilderClass myBuilderClass = new MyBuilderClass();
            myBuilderClass.MyBuilder(getContext(),"All fields are required,,please ensure you have access to the sever");
        }

        else {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final String drnames = DRName.getText().toString();
            final String drnumber = DRNumber.getText().toString();
//        final String drpic = DriverRPic.getText().toString();
            final String drVehicle = DRSpinnerId.getSelectedItem().toString();
            if (drnames.isEmpty() || drnumber.isEmpty()) {
                progressDialog.dismiss();
                MyBuilderClass myBuilderClass = new MyBuilderClass();
                myBuilderClass.MyBuilder(getContext(), "All fields are required");
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_DRIVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Smart Travel");
                        builder.setMessage("Driver has been registered");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DRName.setText("");
                                DRNumber.setText("");
//                        DriverRPic.setImageResource(R.drawable.ic_account);
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
                        Map<String, String> DRMAP = new HashMap<>();
                        DRMAP.put("driver_name", drnames);
                        DRMAP.put("driver_number", drnumber);
                        DRMAP.put("vehicle_plate", drVehicle);
                        return DRMAP;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        }
    }
}
