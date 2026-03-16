package com.group5.gw1.service.impl;

import com.group5.gw1.model.RouteInfo;
import com.group5.gw1.model.ViabilityScore;
import com.group5.gw1.model.WeatherData;
import com.group5.gw1.service.ViabilityScoreService;
import org.springframework.stereotype.Service;

@Service
public class ViabilityScoreServiceImpl implements ViabilityScoreService {

    /**
     * Calculates resource viability based on route and weather data
     */
    @Override
    public ViabilityScore calculateViabilityScore(RouteInfo routeInfo, WeatherData weatherData) {
        // 2. Safety Override Logic
        // If flood warning exists, score must be 0.0
        if (weatherData != null && weatherData.isFloodWarning()) {
            return new ViabilityScore(0.0, 0.0, 0.0, 0.0);
        }

        // 3. Sub-score calculations
        double proximity = (routeInfo != null && routeInfo.getDistanceKm() > 0) ?
                Math.max(0, 1.0 - (routeInfo.getDistanceKm() / 50.0)) : 0.0;

        double accessibility = (routeInfo != null && routeInfo.isValid()) ? 0.8 : 0.2;

        double weatherSafety = (weatherData != null) ?
                Math.max(0, 1.0 - (weatherData.getDroughtIndex() / 10.0)) : 0.5;

        // 4. Final aggregation
        double total = (proximity + accessibility + weatherSafety) / 3.0;

        return new ViabilityScore(total, proximity, accessibility, weatherSafety);
    }
}