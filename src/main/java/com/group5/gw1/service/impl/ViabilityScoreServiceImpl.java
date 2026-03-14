package com.group5.gw1.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group5.gw1.client.OpenMeteoClient;
import com.group5.gw1.client.TomTomClient;
import com.group5.gw1.model.*;
import com.group5.gw1.service.ViabilityScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViabilityScoreServiceImpl implements ViabilityScoreService {

    @Autowired
    private TomTomClient tomTomClient;

    @Autowired
    private OpenMeteoClient openMeteoClient;

    /**
     * Calculates resource viability based on route and weather data
     */
    public ViabilityScore calculateViabilityScore(ResourceSource resourceSource, Location userLocation) throws JsonProcessingException {
        ViabilityScore score = new ViabilityScore();

        // 1. Fetch technical data
        RouteInfo route = tomTomClient.calculateRoute(userLocation, resourceSource.getPosition());
        WeatherRisk weather = openMeteoClient.getWeatherRisk(resourceSource.getPosition());

        // 2. Safety Override Logic
        // If flood warning exists, score must be 0.0
        if (weather != null && weather.isFloodWarning()) {
            score.setTotalScore(0.0);
            score.setWeatherSafetyScore(0.0);
            return score;
        }

        // 3. Sub-score calculations
        double proximity = (route != null && route.getDistanceKm() > 0) ?
                Math.max(0, 1.0 - (route.getDistanceKm() / 50.0)) : 0.0;

        double accessibility = (route != null && route.isValid()) ? 0.8 : 0.2;

        double weatherSafety = (weather != null) ?
                Math.max(0, 1.0 - (weather.getDroughtIndex() / 10.0)) : 0.5;

        // 4. Final aggregation
        double total = (proximity + accessibility + weatherSafety) / 3.0;

        score.setProximityScore(proximity);
        score.setAccessibilityScore(accessibility);
        score.setWeatherSafetyScore(weatherSafety);
        score.setTotalScore(total);

        return score;
    }

    @Override
    public ViabilityScore calculateViabilityScore(ResourceSource resourceSource) {
        return calculateViabilityScore(resourceSource, new Location(0.0, 0.0));
    }
}