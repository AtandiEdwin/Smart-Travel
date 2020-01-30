package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atandi.smarttravel.R;

public class RouteManagementFragment extends Fragment {
    Context context;
    LayoutInflater inflater;
    EditText setVehicleCost;
    Spinner routeSpinner,setVehicleSpinner;
    CheckBox a;
    TextView BookStatusCheck,routSeatsRemain,setRouteId;
    Button BtnManage,BtnUpdateRoute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.route_managemnt,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVehicleCost = view.findViewById(R.id.setVehicleCost);
        routeSpinner = view.findViewById(R.id.routeSpinner);
        setVehicleSpinner = view.findViewById(R.id.setVehicleSpinner);
        BookStatusCheck = view.findViewById(R.id.BookStatusCheck);
        routSeatsRemain = view.findViewById(R.id.routSeatsRemain);
        setRouteId = view.findViewById(R.id.setRouteId);

        BtnManage = view.findViewById(R.id.BtnManage);
        BtnUpdateRoute = view.findViewById(R.id.BtnUpdateRoute);



    }
}
