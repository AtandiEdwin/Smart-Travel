package com.atandi.smarttravel.AdminApp.AdminActivities.AdminAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminFragments.BookedCustomersFragment;
import com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels.PendingUserModel;
import com.atandi.smarttravel.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atandi.smarttravel.Constants.Links.SAVE_DETAILS;
import static com.atandi.smarttravel.Constants.Links.UPDATE_USER_STATUS;

public class PendingUserAdapter extends RecyclerView.Adapter<PendingUserAdapter.ViewHolder> {

    Context context;
    List<PendingUserModel> pUser;

    public PendingUserAdapter(Context context, List<PendingUserModel> pUser) {
        this.context = context;
        this.pUser = pUser;
    }

    interface OnClickLister{
        void onClick(BookedCustomersFragment clickediten);
    }

    private View.OnClickListener mcallback;

    public void  setOnClickListener(View.OnClickListener callback){
        mcallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_user_item,parent,false);
        return new PendingUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        PendingUserModel model = pUser.get(position);
        holder.pendUserPhone.setText(model.getUser_phone());
        holder.pendUserPickPoint.setText(model.getPickpoint());

        holder.pendUserCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.pendUserCheckBox.isChecked()){

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Smart Travel");
                    builder.setMessage("Are you sure the customer as boarded ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final  String status = "booked";
                            final  String uphone = holder.pendUserPhone.getText().toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_USER_STATUS, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    holder.itemView.setVisibility(View.GONE);
                                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }){
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String,String> map = new HashMap<>();
                                    map.put("status",status);
                                    map.put("phone",uphone);
                                    return map;
                                }
                            };
                            RequestQueue mrequestQueue = Volley.newRequestQueue(context);
                            mrequestQueue.add(stringRequest);
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.create().show();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return pUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView pendUserPhone,pendUserPickPoint;
        CheckBox pendUserCheckBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pendUserPhone = itemView.findViewById(R.id.pendUserPhone);
            pendUserPickPoint = itemView.findViewById(R.id.pendUserPickPoint);
            pendUserCheckBox = itemView.findViewById(R.id.pendUserCheckBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if(pos!= RecyclerView.NO_POSITION){

                    }
                }
            });
        }
    }
}
