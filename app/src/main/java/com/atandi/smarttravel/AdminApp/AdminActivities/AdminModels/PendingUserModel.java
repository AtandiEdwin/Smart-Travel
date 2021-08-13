package com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels;

public class PendingUserModel {
    String user_phone,pickpoint;

    public PendingUserModel(String user_phone, String pickpoint) {
        this.user_phone = user_phone;
        this.pickpoint = pickpoint;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getPickpoint() {
        return pickpoint;
    }

    public void setPickpoint(String pickpoint) {
        this.pickpoint = pickpoint;
    }
}
