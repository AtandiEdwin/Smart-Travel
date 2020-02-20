package com.atandi.smarttravel.AdminApp.AdminActivities.AdminModels;

public class Vehicle {
    String vehicle_plate;
    String vehicle_password;

    public Vehicle() {
    }

    public Vehicle(String vehicle_plate, String vehicle_password) {
        this.vehicle_plate = vehicle_plate;
        this.vehicle_password = vehicle_password;
    }

    public String getVehicle_plate() {
        return vehicle_plate;
    }

    public void setVehicle_plate(String vehicle_plate) {
        this.vehicle_plate = vehicle_plate;
    }

    public String getVehicle_password() {
        return vehicle_password;
    }

    public void setVehicle_password(String vehicle_password) {
        this.vehicle_password = vehicle_password;
    }
}
