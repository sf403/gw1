package com.group5.gw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class Gw1Application {

	public static void main(String[] args) {
		SpringApplication.run(Gw1Application.class, args);
        try{
            RouteInfo route = Tomtom.getRouteInfo(new Location(52.50931,13.42936), new Location(52.50274,13.43872));
            System.out.println(route.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
