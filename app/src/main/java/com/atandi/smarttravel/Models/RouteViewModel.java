package com.atandi.smarttravel.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RouteViewModel extends ViewModel {

    private MutableLiveData<List> routepick = new MutableLiveData<>();

    public void setRoutepick(List list){
        routepick.setValue(list);
    }

    public LiveData<List> getRoutepick(){return routepick;}
}
