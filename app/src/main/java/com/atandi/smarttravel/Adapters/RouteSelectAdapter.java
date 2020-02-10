package com.atandi.smarttravel.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.Fragments.RouteSelectingFragment;
import com.atandi.smarttravel.Models.RouteSelectModel;
import com.atandi.smarttravel.R;

import java.util.ArrayList;
import java.util.List;

public class RouteSelectAdapter extends RecyclerView.Adapter<RouteSelectAdapter.MyHolder> {

    Context mctx;

    public List<RouteSelectModel> myRoutes ;

    public RouteSelectAdapter(Context mctx, List<RouteSelectModel> myRoutes) {
        this.mctx = mctx;
        this.myRoutes = myRoutes;
    }

    interface OnClickLister{
        void onClick(RouteSelectingFragment clickediten);
    }

    private View.OnClickListener mcallback;

    public void  setOnClickListener(View.OnClickListener callback){
        mcallback = callback;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View v = inflater.inflate(R.layout.route_item,parent,false);

        return new RouteSelectAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        RouteSelectModel routeSelectModel = myRoutes.get(position);
        holder.textView.setText(routeSelectModel.getRouteName());
    }

    @Override
    public int getItemCount() {
        return myRoutes.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.routeNameId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if(pos!= RecyclerView.NO_POSITION){
                         String rm = myRoutes.get(pos).getRouteName();

                        Intent intent = new Intent("custom-message");
                        intent.putExtra("RouteName",rm);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                    }
                }
            });
        }
    }

}

