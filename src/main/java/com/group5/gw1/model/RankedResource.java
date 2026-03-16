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
}
