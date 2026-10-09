package com.ridewise.service;

import com.ridewise.model.Location;
import com.ridewise.model.Rider;

import java.util.LinkedHashMap;
import java.util.Map;

public class RiderService {
    private final Map<Integer, Rider> riders = new LinkedHashMap<>();
    private int nextId = 1;

    public Rider registerRider(String name, Location location) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Rider name cannot be empty");
        }
        if (location == null) {
            throw new IllegalArgumentException("Rider location is required");
        }
        Rider rider = new Rider(nextId++, name.trim(), location);
        riders.put(rider.getId(), rider);
        return rider;
    }

    public Rider getRider(int id) {
        Rider rider = riders.get(id);
        if (rider == null) {
            throw new IllegalArgumentException("No rider found with id " + id);
        }
        return rider;
    }
}
