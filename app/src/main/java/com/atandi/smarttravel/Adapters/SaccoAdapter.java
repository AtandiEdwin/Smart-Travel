package com.atandi.smarttravel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atandi.smarttravel.Models.SaccoModel;
import com.atandi.smarttravel.R;

import java.util.List;

public class SaccoAdapter extends RecyclerView.Adapter<SaccoAdapter.SaccoViewHolder> {

//    SaccoModel model = new SaccoModel();
    Context context;
    List<SaccoModel> mysaccos;

    public SaccoAdapter() {
    }

    public SaccoAdapter(Context context, List<SaccoModel> mysaccos) {
        this.context = context;
        this.mysaccos = mysaccos;
    }

    @NonNull
    @Override
    public SaccoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.sacco_item,parent,false);
        return new SaccoAdapter.SaccoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SaccoViewHolder holder, int position) {

        SaccoModel model = mysaccos.get(position);
        holder.saccoNameId.setText(model.getSaccoName());

    }

    @Override
    public int getItemCount() {
        return mysaccos.size();
    }

    class SaccoViewHolder extends RecyclerView.ViewHolder{

        TextView saccoNameId;

        public SaccoViewHolder(@NonNull View itemView) {
            super(itemView);
            saccoNameId= itemView.findViewById(R.id.saccoNameId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION){
                        String sacco = mysaccos.get(pos).getSaccoName();

                        Intent intent = new Intent("sacco_name");
                        intent.putExtra("sacco",sacco);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                    }
                }
            });
        }
    }
}
