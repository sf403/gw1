package com.group5.gw1.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.gw1.client.OpenMeteoClient;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.WeatherRisk;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenMeteoClientImpl implements OpenMeteoClient {

    @Override
    public WeatherRisk getWeatherRisk(Location location) {
        try {
            double lat = location.getLat();
            double lon = location.getLon();

            String url = "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=" + lat
                    + "&longitude=" + lon
                    + "&current=precipitation,rain,showers,wind_speed_10m";

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode current = root.path("current");

            double precipitation = current.path("precipitation").asDouble(0.0);
            double rain = current.path("rain").asDouble(0.0);
            double showers = current.path("showers").asDouble(0.0);
            double windSpeed = current.path("wind_speed_10m").asDouble(0.0);

            double floodRisk = precipitation * 4 + rain * 5 + showers * 4 + windSpeed * 0.2;

            if (floodRisk > 100) {
                floodRisk = 100;
            }

            WeatherRisk risk = new WeatherRisk();
            risk.setFloodRisk(floodRisk);
            risk.setHistoricalData(
                    "precipitation=" + precipitation
                            + ", rain=" + rain
                            + ", showers=" + showers
                            + ", windSpeed=" + windSpeed
            );

            return risk;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }
}