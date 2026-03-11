package com.group5.gw1.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;

public interface TomTomClient {
    RouteInfo calculateRoute(Location origin, Location destination) throws JsonProcessingException;
}

