package com.group5.gw1;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Tomtom {
    public static RouteInfo getRouteInfo(Location origin, Location destination) throws IOException, InterruptedException{
        String api_key = "";
        String uri = "https://api.tomtom.com/routing/1/calculateRoute/" +
                origin.toString() +
                ":" +
                destination.toString() +
                "/json?key=" +
                api_key;

        
        System.out.println("uri: " + uri);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        try{
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            JSONObject json = new JSONObject(response.body());
            System.out.println(json.toString());
            JSONObject summary = json.getJSONArray("routes")
                                        .getJSONObject(0)
                                        .getJSONObject("summary");
            double lengthKm = (double) summary.getInt("lengthInMeters") / 1000;
//            not sure about this, it might be worth changing later
            int travelTimeMin = summary.getInt("travelTimeInSeconds") / 60;

            return new RouteInfo(lengthKm, travelTimeMin, true);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
