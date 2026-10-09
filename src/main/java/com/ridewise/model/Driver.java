package com.ridewise.model;

import com.ridewise.Enums.VehicleType;

public class Driver {
    private final int id;
    private final String name;
    private final VehicleType vehicleType;
    private Location currentLocation;
    private boolean available = true;
    private int ridesCompleted = 0;

    public Driver(int id, String name, Location currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
    }

    public double distanceTo(Location target) {
        return currentLocation.distanceTo(target);
    }

    public void recordCompletedRide() {
        ridesCompleted++;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int getRidesCompleted() {
        return ridesCompleted;
    }

    @Override
    public String toString() {
        return "Driver Details ->{id=" + id + ", name=" + name + ", vehicle=" + vehicleType
                + ", location=" + currentLocation + ", available=" + available
                + ", ridesCompleted=" + ridesCompleted + "}";
    }
}
