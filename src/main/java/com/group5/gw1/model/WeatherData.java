package com.group5.gw1.model;

import java.time.LocalDateTime;

public class WeatherData {
    private boolean floodWarning;
    private double droughtIndex;
    private boolean hasHistoricalData;
    private LocalDateTime timestamp;

    public boolean isFloodWarning() {
        return floodWarning;
    }

    public void setFloodWarning(boolean floodWarning) {
        this.floodWarning = floodWarning;
    }

    public void setDroughtIndex(double droughtIndex) {
        this.droughtIndex = droughtIndex;
    }

    public void setHasHistoricalData(boolean hasHistoricalData) {
        this.hasHistoricalData = hasHistoricalData;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getDroughtIndex() {
        return droughtIndex;
    }

    public boolean isHasHistoricalData() {
        return hasHistoricalData;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
