package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.R;

public class BookedCustomersFragment extends Fragment {
    Context context;
    LayoutInflater inflater;
    Spinner pendingUserSpinner,pendingUserRouteSpinner;
    Button BtnLoadUsers;
    RecyclerView pendingUserRecycler;

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



    }
}
