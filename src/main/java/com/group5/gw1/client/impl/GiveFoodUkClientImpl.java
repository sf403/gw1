package com.group5.gw1.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.gw1.client.GiveFookUkClient;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.ResourceType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiveFoodUkClientImpl implements GiveFookUkClient {
    private static final String BASE_URL = "https://www.givefood.org.uk/api/2";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GiveFoodUkClientImpl() {
        this.restClient = RestClient.create(BASE_URL);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<ResourceSource> findNearbyResource(ResourceType type, Location location) {
        if (type != ResourceType.FOOD || location == null) {
            return List.of();
        }

        try {
            String responseBody = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foodbanks/search/")
                            .queryParam("lat_lng", location.uriString())
                            .build())
                    .retrieve()
                    .body(String.class);

            if (responseBody == null || responseBody.isBlank()) {
                return List.of();
            }

            JsonNode response = objectMapper.readTree(responseBody);
            if (!response.isArray()) {
                return List.of();
            }

            List<ResourceSource> resources = new ArrayList<>();
            for (JsonNode foodbank : response) {
                Location resourceLocation = parseLocation(foodbank.path("lat_lng").asText(null));
                if (resourceLocation == null) {
                    continue;
                }

                String sourceId = foodbank.path("id").asText("");
                String name = foodbank.path("name").asText("");
                if (sourceId.isBlank() || name.isBlank()) {
                    continue;
                }

                resources.add(new ResourceSource(sourceId, name, type, resourceLocation));
            }

            return resources;
        } catch (JsonProcessingException | RestClientException exception) {
            throw new IllegalStateException("Failed to fetch nearby food resources from GiveFood", exception);
        }
    }

    private Location parseLocation(String latLng) {
        if (latLng == null || latLng.isBlank()) {
            return null;
        }

        String[] parts = latLng.split(",", 2);
        if (parts.length != 2) {
            return null;
        }

        try {
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());
            return new Location(latitude, longitude);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}