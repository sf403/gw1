// TODO-0: get rid of `lib/` folder as Spring handles all json serialization and deserialization.
package com.group5.gw1;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RouteInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Gw1Application {

    // TODO-1: This method should only contain the line 14 and nothing else
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
