package com.group5.gw1.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;

import tools.jackson.databind.ValueDeserializer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TomTomClient {
    // TODO-4a(Jacky): implement this method by moving code from `Tomtom` class here, create package similar to TODO-3
    //  and implement this
    
    private final RestClient restClient;

    public TomTomClient() throws JsonProcessingException {
        this.restClient = RestClient.create("https://api.tomtom.com/routing/1/calculateRoute");
        System.out.println("this thing runs");
        System.out.println(calculateRoute(new Location(52.50931,13.42936), new Location(52.50274,13.43872)));
    }
    

    // use this page to get information on how to work with jsons in springboot:
    // https://docs.spring.io/spring-boot/reference/features/json.html
    RouteInfo calculateRoute(Location origin, Location destination) throws JsonProcessingException {
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

