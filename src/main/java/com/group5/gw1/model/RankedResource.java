package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankedResource {
    private int rank;

    private ResourceSource source;
    private RouteInfo route;
    private WeatherData weather;
    private RiskAssessment risk;
    private ViabilityScore score;
    private List<TechnicalAlerts> alerts;
    private LocalizedAdvice advice;

    public RankedResource(int rank, ResourceSource source, double total, double proximity, double accessibility, double weatherSafety) {
        this.rank = rank;
        this.source = source;
        this.score = new ViabilityScore(total, proximity, accessibility, weatherSafety);
    }

    // Getters
    public int getRank() {
        return rank;
    }

    public ResourceSource getSource() {
        return source;
    }

    public RouteInfo getRoute() {
        return route;
    }

    public WeatherData getWeather() {
        return weather;
    }

    public RiskAssessment getRisk() {
        return risk;
    }

    public ViabilityScore getScore() {
        return score;
    }

    public List<TechnicalAlerts> getAlerts() {
        return alerts;
    }

    public LocalizedAdvice getAdvice() {
        return advice;
    }
}
