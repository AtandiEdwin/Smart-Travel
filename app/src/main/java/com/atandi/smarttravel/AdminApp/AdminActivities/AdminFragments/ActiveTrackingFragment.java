package com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.R;

public class ActiveTrackingFragment extends Fragment {

    Context context;
    LayoutInflater inflater;
    RecyclerView activeTrackingRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.active_tracking,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activeTrackingRecyclerView = view.findViewById(R.id.activeTrackingRecyclerView);
        activeTrackingRecyclerView.setHasFixedSize(true);
    }
}
