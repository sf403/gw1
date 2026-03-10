package com.group5.gw1.model;

public class RouteInfo {
    private double distanceKm;
    private int travelTimeMin;
    private boolean isValid;

    public RouteInfo(double distanceKm, int travelTimeMin, boolean isValid) {
        this.distanceKm = distanceKm;
        this.travelTimeMin = travelTimeMin;
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "distanceKm=" + distanceKm +
                ", travelTimeMin=" + travelTimeMin +
                ", isValid=" + isValid +
                '}';
    }
}
