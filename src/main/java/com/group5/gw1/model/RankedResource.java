package com.group5.gw1.model;

import java.util.List;

public class RankedResource {
    private int rank;

    private ResourceSource source;
    private RouteInfo route;
    private WeatherData weather;
    private RiskAssessment risk;
    private ViabilityScore score;
    private List<TechnicalAlerts> alerts;
    private LocalizedAdvice advice;
}
