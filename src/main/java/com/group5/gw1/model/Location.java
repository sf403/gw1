package com.group5.gw1.model;

public class Location {
    private double lat;
    private double lon;

    public Location(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Location (" + lat + ", " + lon + ')';
    }
}
