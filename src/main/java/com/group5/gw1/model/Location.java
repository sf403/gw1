package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
