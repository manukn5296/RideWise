package com.ridewise.model;

public class Rider {
    private int id;
    private String name;
    private Location location;

    public Rider(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Rider Details-> {id=" + id + ", name=" + name + ", location=" + location+"}";
    }
}
