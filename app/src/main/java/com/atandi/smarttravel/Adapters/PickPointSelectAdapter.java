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

import com.atandi.smarttravel.Fragments.RouteSelectingFragment;
import com.atandi.smarttravel.Models.PickPointSelectModel;
import com.atandi.smarttravel.R;

import java.util.List;
public class PickPointSelectAdapter extends RecyclerView.Adapter<PickPointSelectAdapter.MyHolder> {

        Context mctx;

        public List<PickPointSelectModel> myPicks ;

        public PickPointSelectAdapter(Context mctx, List<PickPointSelectModel> myPicks) {
            this.mctx = mctx;
            this.myPicks = myPicks;
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


            View v = inflater.inflate(R.layout.pick_item,parent,false);

            return new PickPointSelectAdapter.MyHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
            PickPointSelectModel pickPointSelectModel = myPicks.get(position);
            holder.textView.setText(pickPointSelectModel.getPickname());
        }

        @Override
        public int getItemCount() {
            return myPicks.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder{

            TextView textView;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.pickNameId);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            String rm = myPicks.get(pos).getPickname();

                            Intent intent = new Intent("custom-message1");
                            intent.putExtra("PickName",rm);
                            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                        }
                    }
                });
            }
        }
}
