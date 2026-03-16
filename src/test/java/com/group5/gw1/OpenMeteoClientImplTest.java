package com.group5.gw1.client.impl;

import com.group5.gw1.client.OpenMeteoClient;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.WeatherRisk;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenMeteoClientImplTest {

    @Test
    void shouldReturnWeatherRiskForValidLocation() {
        OpenMeteoClient client = new OpenMeteoClientImpl();
        Location location = new Location(51.5074, -0.1278); // London

        WeatherRisk risk = client.getWeatherRisk(location);

        assertNotNull(risk);
        assertNotNull(risk.getHistoricalData());
        assertTrue(risk.getFloodRisk() >= 0);
        System.out.println(risk.getFloodRisk());
        System.out.println(risk.getHistoricalData());
    }
}