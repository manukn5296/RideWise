package com.ridewise.service;

import com.ridewise.model.Driver;
import com.ridewise.Enums.VehicleType;
import com.ridewise.model.Location;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverService {
    private final Map<Integer, Driver> drivers = new LinkedHashMap<>();
    private int nextId = 1;

    public Driver registerDriver(String name, Location location, VehicleType vehicleType) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }
        if (location == null || vehicleType == null) {
            throw new IllegalArgumentException("Driver location and vehicle type are required");
        }
        Driver driver = new Driver(nextId++, name.trim(), location, vehicleType);
        drivers.put(driver.getId(), driver);
        System.out.println(drivers);
        return driver;
    }

    public Driver getDriver(int id) {
        Driver driver = drivers.get(id);
        if (driver == null) {
            throw new IllegalArgumentException("No driver found with id " + id);
        }
        return driver;
    }

    public void updateAvailability(int driverId, boolean available) {
        getDriver(driverId).setAvailable(available);
    }

    public List<Driver> listAvailableDrivers() {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    public void releaseAfterTrip(Driver driver) {
        driver.recordCompletedRide();
        driver.setAvailable(true);
    }
}
