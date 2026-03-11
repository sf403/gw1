package com.group5.gw1.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.gw1.client.TomTomClient;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TomTomClientImpl implements TomTomClient {
    private final RestClient restClient;

    public TomTomClientImpl() throws JsonProcessingException {
        this.restClient = RestClient.create("https://api.tomtom.com/routing/1/calculateRoute");
        System.out.println(calculateRoute(new Location(52.50931,13.42936), new Location(52.50274,13.43872)));
    }

    @Override
    public RouteInfo calculateRoute(Location origin, Location destination) throws JsonProcessingException {
        String response = this.restClient.get().uri("/{origin.uriString()}:{destination.uriString()}/json?key=Jlgx8XukPhzgG2mSvOv5qsHLs3Oui97X",
                origin.uriString(),
                destination.uriString()
        ).retrieve().body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode summary = mapper.readTree(response).
                get("routes").
                get(0).
                get("summary");
        return new RouteInfo(
                (double) summary.get("lengthInMeters").asInt() / 1000,
                summary.get("travelTimeInSeconds").asInt() / 60,
                true);
    }
}

