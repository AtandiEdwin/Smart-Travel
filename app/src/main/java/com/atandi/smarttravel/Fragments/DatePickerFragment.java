package com.atandi.smarttravel.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String tag;

    String xx;
    public  void setXx(String s){
        this.xx = s;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Bundle b = new Bundle();
        tag = getArguments().getString("from");

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(view.isShown()){
            if(tag.equals("from")){
                String fromdate = year+"-"+ month+1 +"-"+dayOfMonth ;
                Intent fromintent = new Intent("from-date");
                fromintent.putExtra("fromdate",fromdate);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(fromintent);
            }
            else if(tag.equals("to")){
                String todate = year +"-"+ month+1 +"-"+dayOfMonth;
                Intent tointent = new Intent("to-date");
                tointent.putExtra("todate",todate);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(tointent);
            }
            else{
                String date = dayOfMonth +"-"+ month+1 +"-"+year;
                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
