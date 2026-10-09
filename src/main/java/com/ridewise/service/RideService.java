package com.ridewise.service;

import com.ridewise.model.Driver;
import com.ridewise.model.FareReceipt;
import com.ridewise.model.Ride;
import com.ridewise.model.Rider;
import com.ridewise.strategy.FareStrategy;
import com.ridewise.strategy.RideMatchingStrategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RideService {
    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;

    private final Map<Integer, Ride> rides = new LinkedHashMap<>();
    private final Map<Integer, FareReceipt> receipts = new LinkedHashMap<>();
    private int nextId = 1;

    public RideService(RiderService riderService,
                       DriverService driverService,
                       RideMatchingStrategy matchingStrategy,
                       FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(int riderId, double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0");
        }
        Rider rider = riderService.getRider(riderId);
        Ride ride = new Ride(nextId++, rider, distance);

        Driver driver = matchingStrategy.findDriver(rider, driverService.listAvailableDrivers());
        if (driver == null) {
            ride.cancel();
            rides.put(ride.getId(), ride);
            throw new IllegalStateException(
                    "No drivers available right now (ride " + ride.getId() + " cancelled)");
        }

        ride.assignDriver(driver);
        driverService.updateAvailability(driver.getId(), false);
        rides.put(ride.getId(), ride);
        return ride;
    }

    public FareReceipt completeRide(int rideId) {
        Ride ride = getRide(rideId);
        ride.complete();
        driverService.releaseAfterTrip(ride.getDriver());

        FareReceipt receipt = new FareReceipt(ride.getId(), fareStrategy.calculateFare(ride));
        receipts.put(ride.getId(), receipt);
        return receipt;
    }

    public void cancelRide(int rideId) {
        Ride ride = getRide(rideId);
        Driver driver = ride.getDriver();
        ride.cancel();
        if (driver != null) {
            driverService.updateAvailability(driver.getId(), true);
        }
    }

    public Ride getRide(int rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("No ride found with id " + rideId);
        }
        return ride;
    }

    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }
}
