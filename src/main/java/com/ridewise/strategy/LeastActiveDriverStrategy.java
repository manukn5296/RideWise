package com.ridewise.strategy;

import com.ridewise.model.Driver;
import com.ridewise.model.Rider;

import java.util.Comparator;
import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        return drivers.stream()
                .min(Comparator.comparingInt(Driver::getRidesCompleted)
                        .thenComparingDouble(d -> d.distanceTo(rider.getLocation())))
                .orElse(null);
    }
}
