package com.ridewise.strategy;

import com.ridewise.Enums.VehicleType;
import com.ridewise.model.Ride;

import java.util.EnumMap;
import java.util.Map;

public class DefaultFareStrategy implements FareStrategy {
    private final Map<VehicleType, Double> baseFare = new EnumMap<>(VehicleType.class);
    private final Map<VehicleType, Double> perKmRate = new EnumMap<>(VehicleType.class);

    public DefaultFareStrategy() {
        baseFare.put(VehicleType.BIKE, 20.0);
        baseFare.put(VehicleType.AUTO, 30.0);
        baseFare.put(VehicleType.CAR, 50.0);

        perKmRate.put(VehicleType.BIKE, 8.0);
        perKmRate.put(VehicleType.AUTO, 12.0);
        perKmRate.put(VehicleType.CAR, 18.0);
    }

    @Override
    public double calculateFare(Ride ride) {
        VehicleType type = ride.getVehicleType();
        return baseFare.get(type) + perKmRate.get(type) * ride.getDistance();
    }
}
