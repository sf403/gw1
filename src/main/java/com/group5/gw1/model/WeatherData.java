package com.group5.gw1.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WeatherData {
    private boolean floodWarning;
    private double droughtIndex;
    private boolean hasHistoricalData;
    private LocalDateTime timestamp;
}
