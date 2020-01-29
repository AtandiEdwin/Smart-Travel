package com.atandi.smarttravel.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SummaryViewModel extends ViewModel {
    private MutableLiveData<List> summary = new MutableLiveData<>();

    public void setSummary(List list){
        summary.setValue(list);
    }

   public LiveData<List> getSummary(){
        return summary;
   }

}
