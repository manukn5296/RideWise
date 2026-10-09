package com.ridewise.strategy;

import com.ridewise.model.Ride;

/** Wraps another FareStrategy and applies a surge multiplier (DRY: no duplicated rate tables). */
public class PeakHourFareStrategy implements FareStrategy {
    private final FareStrategy baseStrategy;
    private final double surgeMultiplier;

    public PeakHourFareStrategy(FareStrategy baseStrategy, double surgeMultiplier) {
        if (surgeMultiplier < 1.0) {
            throw new IllegalArgumentException("Surge multiplier must be >= 1.0");
        }
        this.baseStrategy = baseStrategy;
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculateFare(Ride ride) {
        return baseStrategy.calculateFare(ride) * surgeMultiplier;
    }
}
