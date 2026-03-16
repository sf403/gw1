package com.group5.gw1.controller;

import com.group5.gw1.client.GiveFookUkClient;
import com.group5.gw1.client.OpenMeteoClient;
import com.group5.gw1.client.TomTomClient;
import com.group5.gw1.dto.ResourceRequest;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.RouteInfo;
import com.group5.gw1.model.ViabilityScore;
import com.group5.gw1.model.WeatherData;
import com.group5.gw1.model.WeatherRisk;
import com.group5.gw1.service.ViabilityScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private GiveFookUkClient giveFoodUkClient;

    @Autowired
    private TomTomClient tomTomClient;

    @Autowired
    private OpenMeteoClient openMeteoClient;

    @Autowired
    private ViabilityScoreService viabilityScoreService;

    @PostMapping
    public List<ResourceSource> getRankedResources(@RequestBody ResourceRequest request) {

        List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(request.getType(), request.getLocation());
        
        // Map to keep track of calculated viability scores for each source
        Map<ResourceSource, ViabilityScore> scoresList = new HashMap<>();

        for (ResourceSource source : resources) {
            try {
                // 1. Get Route
                RouteInfo routeInfo = tomTomClient.calculateRoute(request.getLocation(), source.getPosition());
                
                // 2. Get Weather Risk
                // Assuming we use source position or route coordinate based on the sequence diagram.
                WeatherRisk weatherRisk = openMeteoClient.getWeatherRisk(source.getPosition());

                // 3. Map WeatherRisk to WeatherData because the service expects WeatherData
                WeatherData weatherData = new WeatherData();
                weatherData.setFloodWarning(weatherRisk != null && weatherRisk.getFloodRisk() > 0.5);
                weatherData.setDroughtIndex(weatherRisk != null && weatherRisk.getHistoricalData() != null ? 5.0 : 0.0); // Basic mapping
                weatherData.setHasHistoricalData(weatherRisk != null && weatherRisk.getHistoricalData() != null);
                weatherData.setTimestamp(LocalDateTime.now());

                // 4. Calculate Score
                ViabilityScore viabilityScore = viabilityScoreService.calculateViabilityScore(routeInfo, weatherData);
                scoresList.put(source, viabilityScore);

            } catch (Exception e) {
                // If anything fails, put a zero score or ignore
                scoresList.put(source, new ViabilityScore(0.0, 0.0, 0.0, 0.0));
            }
        }

        // 5. Sort the list of resources based on the viability scores (highest first)
        resources.sort((r1, r2) -> {
            ViabilityScore s1 = scoresList.get(r1);
            ViabilityScore s2 = scoresList.get(r2);
            double total1 = s1 != null ? s1.getTotalScore() : 0.0;
            double total2 = s2 != null ? s2.getTotalScore() : 0.0;
            return Double.compare(total2, total1); // descending
        });

        return resources;
    }
}