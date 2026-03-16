package com.group5.gw1.model;

public class ViabilityScore {
    public ViabilityScore(double total, double proximity, double accessibility, double weatherSafety) {
        this.totalScore = total;
        this.proximityScore = proximity;
        this.accessibilityScore = accessibility;
        this.weatherSafetyScore = weatherSafety;
    }
    private double totalScore;
    private double proximityScore;
    private double accessibilityScore;
    private double weatherSafetyScore;

    public double getTotalScore() {
        return totalScore;
    }

    public double getProximityScore() {
        return proximityScore;
    }

    public double getAccessibilityScore() {
        return accessibilityScore;
    }

    public double getWeatherSafetyScore() {
        return weatherSafetyScore;
    }
}
