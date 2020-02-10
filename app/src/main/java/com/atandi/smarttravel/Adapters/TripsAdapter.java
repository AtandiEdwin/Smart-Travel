package com.atandi.smarttravel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.Models.TripsModel;
import com.atandi.smarttravel.R;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<MyTripViewHolder> {

    Context context;
    List<TripsModel> mtrip;

    public TripsAdapter(Context context, List<TripsModel> mtrip) {
        this.context = context;
        this.mtrip = mtrip;
    }

    public TripsAdapter() {
    }



    @NonNull
    @Override
    public MyTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.trip_item,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new MyTripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTripViewHolder holder, int position) {

        TripsModel model = mtrip.get(position);


        holder.tripRouteName.setText(model.getRouteName());
        holder.tripVehicle.setText((model.getVehiclePlate()).toUpperCase());
        holder.tripDate.setText(model.getTripDate());
        String cost ="Ksh: " + model.getTripCost();
        holder.tripCost.setText(cost);
        holder.seatsBooked.setText(model.getSeatsbooked());
        holder.tripstatus.setText(model.getTripstatus());

    }

    @Override
    public int getItemCount() {
        return mtrip.size();
    }
}

class MyTripViewHolder extends RecyclerView.ViewHolder{

    TextView tripRouteName,tripCost,tripDate,tripVehicle,seatsBooked,tripstatus;

    public MyTripViewHolder(@NonNull View itemView) {
        super(itemView);

        tripCost = itemView.findViewById(R.id.costId);
        tripDate = itemView.findViewById(R.id.dateIid);
        tripVehicle = itemView.findViewById(R.id.vehicleId);
        tripRouteName = itemView.findViewById(R.id.routename);
        seatsBooked = itemView.findViewById(R.id.seatsBooked);
        tripstatus = itemView.findViewById(R.id.tripstatus);

    }
}