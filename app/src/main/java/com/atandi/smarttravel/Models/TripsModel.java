package com.atandi.smarttravel.Models;

public class TripsModel {
   private String routeName;
   private String vehiclePlate;
   private String tripCost;
   private String tripDate;
   private String seatsbooked;
   private String tripstatus;

    public TripsModel(String routeName, String vehiclePlate, String tripCost, String tripDate, String seatsbooked, String tripstatus) {
        this.routeName = routeName;
        this.vehiclePlate = vehiclePlate;
        this.tripCost = tripCost;
        this.tripDate = tripDate;
        this.seatsbooked = seatsbooked;
        this.tripstatus = tripstatus;
    }


    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getTripCost() {
        return tripCost;
    }

    public void setTripCost(String tripCost) {
        this.tripCost = tripCost;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getSeatsbooked() {
        return seatsbooked;
    }

    public void setSeatsbooked(String seatsbooked) {
        this.seatsbooked = seatsbooked;
    }

    public String getTripstatus() {
        return tripstatus;
    }

    public void setTripstatus(String tripstatus) {
        this.tripstatus = tripstatus;
    }
}
