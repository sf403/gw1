package com.group5.gw1;

public class RouteInfo {
    public double distanceKm;
    public int travelTimeMin;
    public boolean isValid;

    public RouteInfo(double distanceKm, int travelTimeMin, boolean isValid){
        this.distanceKm = distanceKm;
        this.travelTimeMin = travelTimeMin;
        this.isValid = isValid;
    }

    public String toString(){
        return "distanceKm: " + String.valueOf(distanceKm) 
                + " travelTImeMin " + String.valueOf(travelTimeMin)
                + " isValid " + String.valueOf(isValid);
    }
}
