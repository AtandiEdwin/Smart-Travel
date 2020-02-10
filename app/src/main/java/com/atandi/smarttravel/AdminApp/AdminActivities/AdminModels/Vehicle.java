package com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels;

public class Vehicle {
    String vehicle_plate;
    String Id;

    public Vehicle(String vehicle_plate, String id) {
        this.vehicle_plate = vehicle_plate;
        Id = id;
    }

    public String getVehicle_plate() {
        return vehicle_plate;
    }

    public void setVehicle_plate(String vehicle_plate) {
        this.vehicle_plate = vehicle_plate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
