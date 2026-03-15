package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RouteInfo {
    private double distanceKm;
    private int travelTimeMin;
    private boolean isValid;
}