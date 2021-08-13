package com.atandi.smarttravel.Models;

public class PickPointSelectModel {
    String pickname;

    public PickPointSelectModel() {
    }

    public PickPointSelectModel(String pickname) {
        this.pickname = pickname;
    }

    public String getPickname() {
        return pickname;
    }

    public void setPickname(String pickname) {
        this.pickname = pickname;
    }
}
