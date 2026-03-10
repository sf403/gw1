package com.group5.gw1.client;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;

public interface TomTomClient {
    // TODO-4a(Jacky): implement this method by moving code from `Tomtom` class here, create package similar to TODO-3
    //  and implement this
    RouteInfo calculateRoute(Location origin, Location destination);
}
