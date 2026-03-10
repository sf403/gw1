package com.group5.gw1.model;

public class Location {
    private double lat;
    private double lon;

    public Location(double lat, double lon){
        this.lon = lon;
        this.lat = lat;
    }

    public String uriString(){
        return lat + "," + lon;
    }

    @Override
    public String toString() {
        return "Location (" + lat + ", " + lon + ')';
    }
}
