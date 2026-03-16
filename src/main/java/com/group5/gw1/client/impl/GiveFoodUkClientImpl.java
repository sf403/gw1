package com.group5.gw1.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.gw1.client.GiveFookUkClient;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiveFoodUkClientImpl implements GiveFookUkClient {
    private static final Logger LOG = LoggerFactory.getLogger(GiveFoodUkClientImpl.class);
    private static final String BASE_URL = "https://www.givefood.org.uk/api/2";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GiveFoodUkClientImpl() {
        this.restClient = RestClient.create(BASE_URL);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<ResourceSource> findNearbyResource(ResourceType type, Location location) {
        if (type != ResourceType.FOOD || !isValidLocation(location)) {
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
        } catch (RestClientResponseException response) {
            int statusCode = response.getStatusCode() != null ? response.getStatusCode().value() : -1;
            LOG.warn("GiveFood request failed (status={}): {}", statusCode, response.getResponseBodyAsString());
            // GiveFood returns 400 for invalid coordinates (e.g. 0,0 or invalid format)
            return List.of();
        } catch (RestClientException exception) {
            LOG.warn("Failed to fetch nearby food resources from GiveFood", exception);
            return List.of();
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to fetch nearby food resources from GiveFood", exception);
        }
    }

    private boolean isValidLocation(Location location) {
        if (location == null) {
            return false;
        }

        // GiveFood API returns 400 for invalid coordinates such as 0,0 or out-of-range values
        double lat = location.getLat();
        double lon = location.getLon();
        if (lat == 0.0 && lon == 0.0) {
            return false;
        }

        return lat >= -90.0 && lat <= 90.0 && lon >= -180.0 && lon <= 180.0;
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