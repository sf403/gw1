package com.group5.gw1.controller;

import com.group5.gw1.client.GiveFookUkClient;
import com.group5.gw1.client.OpenMeteoClient;
import com.group5.gw1.client.TomTomClient;
import com.group5.gw1.dto.RankedResourceResult;
import com.group5.gw1.dto.ResourceRequest;
import com.group5.gw1.model.*;
import com.group5.gw1.service.ViabilityScoreService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<?> getRankedResources(@RequestBody ResourceRequest request) {
        if (request == null || request.getLocation() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", java.time.Instant.now().toString());
            errorResponse.put("status", 400);
            errorResponse.put("error", "Bad Request");
            errorResponse.put("path", "/api/resources");
            return ResponseEntity.status(400).body(errorResponse);
        }

        List<ResourceSource> resources = new ArrayList<>(
                giveFoodUkClient.findNearbyResource(request.getType(), request.getLocation()));

        if (resources.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", java.time.Instant.now().toString());
            errorResponse.put("status", 404);
            errorResponse.put("error", "Not Found");
            errorResponse.put("path", "/api/resources");
            return ResponseEntity.status(404).body(errorResponse);
        }

        List<RankedResource> rankedResources = new ArrayList<>();

        for (ResourceSource source : resources) {
            RankedResource ranked = new RankedResource();
            ranked.setSource(source);

            try {
                // 1. Get Route
                RouteInfo routeInfo = tomTomClient.calculateRoute(request.getLocation(), source.getPosition());
                ranked.setRoute(routeInfo);

                // 2. Get Weather Risk
                WeatherRisk weatherRisk = openMeteoClient.getWeatherRisk(source.getPosition());

                // 3. Map WeatherRisk to WeatherData because the service expects WeatherData
                WeatherData weatherData = new WeatherData();
                weatherData.setFloodWarning(weatherRisk != null && weatherRisk.getFloodRisk() > 50.0);
                weatherData.setDroughtIndex(weatherRisk != null && weatherRisk.getHistoricalData() != null ? 5.0 : 0.0);
                weatherData.setHasHistoricalData(weatherRisk != null && weatherRisk.getHistoricalData() != null);
                weatherData.setTimestamp(LocalDateTime.now());
                ranked.setWeather(weatherData);

                // 4. Calculate Score
                ViabilityScore viabilityScore = viabilityScoreService.calculateViabilityScore(routeInfo, weatherData);
                if (viabilityScore != null) {
                    ranked.setScore(viabilityScore);
                } else {
                    ranked.setScore(new ViabilityScore(0.0, 0.0, 0.0, 0.0));
                }

            } catch (Exception e) {
                // If anything fails, put a zero score
                System.out.println("Error processing resource " + source.getName() + ": " + e.getMessage());
                ranked.setScore(new ViabilityScore(0.0, 0.0, 0.0, 0.0));
            }

            rankedResources.add(ranked);
        }

        // 5. Sort the list of RankedResources based on the viability scores (highest
        // first)
        rankedResources.sort((r1, r2) -> {
            double total1 = r1.getScore() != null ? r1.getScore().getTotalScore() : 0.0;
            double total2 = r2.getScore() != null ? r2.getScore().getTotalScore() : 0.0;
            return Double.compare(total2, total1); // descending
        });

        // 6. Assign final ranks (optional, but keeping it in the model)
        for (int i = 0; i < rankedResources.size(); i++) {
            rankedResources.get(i).setRank(i + 1);
        }

        // 7. Map to the final custom JSON representation shown in the screenshot
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (RankedResource rr : rankedResources) {
            Map<String, Object> item = getStringObjectMap(rr);
            responseList.add(item);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("rankedList", responseList);
        responseBody.put("workerLocation", request.getLocation());

        return ResponseEntity.ok(responseBody);
    }

    private static @NonNull Map<String, Object> getStringObjectMap(RankedResource rr) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", rr.getSource().getSourceId());
        item.put("name", rr.getSource().getName());

        // Convert 0.0-1.0 score to 0-100 integer
        int scoreVal = rr.getScore() != null ? (int) Math.round(rr.getScore().getTotalScore() * 100) : 0;
        item.put("score", scoreVal);

        // Format distance string
        if (rr.getRoute() != null) {
            item.put("distance", String.format(java.util.Locale.US, "%.1fkm", rr.getRoute().getDistanceKm()));
        } else {
            item.put("distance", "N/A");
        }
        return item;
    }
}