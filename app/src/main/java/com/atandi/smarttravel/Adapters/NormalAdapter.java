package com.atandi.smarttravel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.Models.NormalModel;
import com.atandi.smarttravel.R;

import java.util.List;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.NormalViewHolder>{
    Context context;
    List<NormalModel> list;

    public NormalAdapter() {
    }

    public NormalAdapter(Context context, List<NormalModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.track_route_item,parent,false);
        return new NormalAdapter.NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalViewHolder holder, int position) {

        NormalModel model = list.get(position);
        holder.pname.setText(model.getpName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{
        TextView pname;
        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
           pname = itemView.findViewById(R.id.trackRouteId);
        }
    }
}
