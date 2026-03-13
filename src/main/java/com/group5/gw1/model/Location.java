package com.group5.gw1.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Location {

    private double lat;
    private double lon;

    @Override
    public String toString() {
        return "Location (" + lat + ", " + lon + ')';
    }
}
