package com.atandi.smarttravel.EditFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atandi.smarttravel.R;

public class TrackingTypeFragment extends Fragment {

    public TrackingTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracking_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout normalTrackingId = view.findViewById(R.id.normalTrackingId);
        LinearLayout mapTrackingId = view.findViewById(R.id.mapTrackingId);

        normalTrackingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartFragment(new NormalTrackingFragment(),"Tracking");

            }
        });

        mapTrackingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartFragment(new KeyFragment(),"Tracking");
            }
        });
    }


    private void StartFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,title);
        fragmentTransaction.replace(R.id.Framelayoutid,fragment);
        fragmentTransaction.commit();
    }
}
