package com.group5.gw1;

public class Location {
    public double lat;
    public double lon;
    
    /**
     * not sure if Pair<> would be better but this follows the class diagram better
     * @param lat
     * @param lon
     */
    public Location(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public String toString(){
        return String.valueOf(lat) + "," + String.valueOf(lon);
    }
}
