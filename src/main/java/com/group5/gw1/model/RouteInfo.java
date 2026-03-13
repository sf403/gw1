package com.group5.gw1.model;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.boot.jackson.JacksonComponent;

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
