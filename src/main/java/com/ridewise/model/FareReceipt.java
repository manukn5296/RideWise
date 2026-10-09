package com.ridewise.model;

import java.time.LocalDateTime;

public class FareReceipt {
    private final int rideId;
    private final double amount;
    private final LocalDateTime generatedAt;

    public FareReceipt(int rideId, double amount) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = LocalDateTime.now();
    }

    public int getRideId() { return rideId; }
    public double getAmount() { return amount; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }

    @Override
    public String toString() {
        return String.format("FareReceipt{rideId=%d, amount=Rs.%.2f, generatedAt=%s}",
                rideId, amount, generatedAt);
    }
}
