package com.group5.gw1.model;

public class WeatherRisk {
    private double floodRisk;
    private String historicalData;

    public void setFloodRisk(double floodRisk) {
        this.floodRisk = floodRisk;
    }

    public void setHistoricalData(String historicalData) {
        this.historicalData = historicalData;
    }

    public double getFloodRisk() {
        return floodRisk;
    }

    public String getHistoricalData() {
        return historicalData;
    }
}
