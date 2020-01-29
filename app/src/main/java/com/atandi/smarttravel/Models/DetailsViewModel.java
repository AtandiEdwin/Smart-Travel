package com.atandi.smarttravel.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private  MutableLiveData<List> mlist = new MutableLiveData<List>();

    public void setMlist(List item) {
        mlist.setValue(item);
    }

    public LiveData<List> getmlist() {
        return mlist;
    }

}
