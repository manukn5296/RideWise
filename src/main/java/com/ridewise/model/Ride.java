package com.ridewise.model;

import com.ridewise.Enums.RideStatus;
import com.ridewise.Enums.VehicleType;

public class Ride {
    private final int id;
    private final Rider rider;
    private final double distance;
    private Driver driver;
    private RideStatus status = RideStatus.REQUESTED;

    public Ride(int id, Rider rider, double distance) {
        this.id = id;
        this.rider = rider;
        this.distance = distance;
    }

    public void assignDriver(Driver driver) {
        requireStatus(RideStatus.REQUESTED, "assigned");
        this.driver = driver;
        this.status = RideStatus.ASSIGNED;
    }

    public void complete() {
        requireStatus(RideStatus.ASSIGNED, "completed");
        this.status = RideStatus.COMPLETED;
    }

    public void cancel() {
        if (status == RideStatus.COMPLETED || status == RideStatus.CANCELLED) {
            throw new IllegalStateException("Ride " + id + " is already " + status);
        }
        this.status = RideStatus.CANCELLED;
    }

    public VehicleType getVehicleType() {
        if (driver == null) {
            throw new IllegalStateException("Ride " + id + " has no driver yet");
        }
        return driver.getVehicleType();
    }

    private void requireStatus(RideStatus expected, String action) {
        if (status != expected) {
            throw new IllegalStateException(
                    "Ride " + id + " cannot be " + action + " from status " + status);
        }
    }

    public int getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public double getDistance() { return distance; }
    public RideStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Ride Details-> {id=" + id + ", rider=" + rider.getName()
                + ", driver=" + (driver == null ? "-" : driver.getName())
                + ", distance=" + distance + " km, status=" + status + "}";
    }
}
