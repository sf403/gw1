package com.group5.gw1.client;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.WeatherRisk;

public interface OpenMeteoClient {
    // TODO-5(Myrza): the `location` passed here should be the "current location of user", create package similar to TODO-3 and implement this
    WeatherRisk getWeatherRisk(Location location);
}
