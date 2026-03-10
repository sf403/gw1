package com.group5.gw1.client;

import org.springframework.http.MediaType;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TomTomClient {
    // TODO-4a(Jacky): implement this method by moving code from `Tomtom` class here, create package similar to TODO-3
    //  and implement this
    
    private final RestClient restClient;

    public TomTomClient(){
        this.restClient = RestClient.create("https://api.tomtom.com/routing/1/calculateRoute");
        System.out.println("this thing runs");
        calculateRoute(new Location(52.50931,13.42936), new Location(52.50274,13.43872));
    }
    

    // use this page to get information on how to work with jsons in springboot:
    // https://docs.spring.io/spring-boot/reference/features/json.html
    RouteInfo calculateRoute(Location origin, Location destination){
        System.out.println(origin.uriString());
        String value = this.restClient.get().uri("/{origin.uriString()}:{destination.uriString()}/json?key=Jlgx8XukPhzgG2mSvOv5qsHLs3Oui97X",
            origin.uriString(),
            destination.uriString()
        ).retrieve().body(String.class);
        System.out.println(value);
        return null;
    }
}
