package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {

    private double lat;
    private double lon;

    // No-arg constructor for JSON deserialization
    public Location() {
    }

    public String uriString(){
        return lat + "," + lon;
    }

    @Override
    public String toString() {
        return "Location (" + lat + ", " + lon + ')';
    }

    // Setters for JSON deserialization
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
