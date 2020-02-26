package com.atandi.smarttravel.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.Models.DetailsViewModel;
import com.atandi.smarttravel.Models.SummaryViewModel;
import com.atandi.smarttravel.R;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private DetailsViewModel detailsViewModel;
    private SummaryViewModel summaryViewModel;
    TextView plateNumberId,driverNameId,driverPhoneId,numberSeatsId,seatsRemainingId,tripcost;
    MyBuilderClass myBuilderClass = new MyBuilderClass();

    ProgressDialog progressDialog;

    public BookingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vehicle_booking_layout,null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        detailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        detailsViewModel.getmlist().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                plateNumberId.setText(list.get(0).toString());
                numberSeatsId.setText(list.get(2).toString());
                seatsRemainingId.setText(list.get(3).toString());
                tripcost.setText(list.get(4).toString());
                driverNameId.setText(list.get(5).toString());
                driverPhoneId.setText(list.get(6).toString());
            }
        });

        summaryViewModel = ViewModelProviders.of(getActivity()).get(SummaryViewModel.class);
        summaryViewModel.getSummary().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText bookedSeatsId= view.findViewById(R.id.bookedSeatsId);


        ImageButton btncallId = view.findViewById(R.id.btncallId);
        Button BtnBook = view.findViewById(R.id.bookNowId);
        plateNumberId = view.findViewById(R.id.plateNumberId);
        tripcost = view.findViewById(R.id.tripcost);
        numberSeatsId= view.findViewById(R.id.numberSeatsId);
        seatsRemainingId = view.findViewById(R.id.seatsRemainingId);
        driverNameId = view.findViewById(R.id.driverNameId);
        driverPhoneId = view.findViewById(R.id.driverPhoneId);
        final EditText customerPhoneId = view.findViewById(R.id.customerPhoneId);

        btncallId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = driverPhoneId.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            }
        });

        BtnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if(bookedSeatsId.getText().toString().isEmpty() ||customerPhoneId.getText().toString().isEmpty()){
                    progressDialog.dismiss();

                    myBuilderClass.MyBuilder(getContext()," please check that you have provided the required details");
                }
                else{
                    List finaldetails  = new ArrayList();
                    int NSEAT = Integer.parseInt(bookedSeatsId.getText().toString());
                    int price = Integer.parseInt(tripcost.getText().toString());
                    int totalprice = NSEAT * price;



//                    final String finalprice= String.valueOf(totalprice);
                    finaldetails.add(totalprice);
                    finaldetails.add(plateNumberId.getText());
                    finaldetails.add(bookedSeatsId.getText().toString());
                    finaldetails.add(customerPhoneId.getText().toString());

                    summaryViewModel.setSummary(finaldetails);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment,new SummaryFragment());
                    fragmentTransaction.commit();
                    progressDialog.dismiss();
                }
            }
        });
    }
}
