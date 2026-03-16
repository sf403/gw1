package com.group5.gw1.client.impl;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.ResourceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiveFoodUkClientImplTest {

    @Autowired
    private GiveFoodUkClientImpl giveFoodUkClient;

    @Test
    void testFindNearbyResource_ValidLocation() {
        // Test with a valid location (London coordinates)
        Location location = new Location(51.5074, -0.1278);
        List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(ResourceType.FOOD, location);

        assertNotNull(resources);
        // Should return some food banks
        assertTrue(resources.size() > 0, "Should find nearby food banks");

        // Check that each resource has required fields
        for (ResourceSource resource : resources) {
            assertNotNull(resource.getSourceId());
            assertNotNull(resource.getName());
            assertEquals(ResourceType.FOOD, resource.getType());
            assertNotNull(resource.getPosition());
        }
    }

    @Test
    void testFindNearbyResource_InvalidLocation() {
        // Test with invalid location (0,0)
        Location location = new Location(0.0, 0.0);
        List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(ResourceType.FOOD, location);

        assertNotNull(resources);
        // Should return empty list for invalid coordinates
        assertEquals(0, resources.size());
    }

    @Test
    void testFindNearbyResource_NonFoodType() {
        Location location = new Location(51.5074, -0.1278);
        List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(ResourceType.CLEAN_WATER, location);

        assertNotNull(resources);
        assertEquals(0, resources.size());
    }

    @Test
    void testFindNearbyResource_NullLocation() {
        List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(ResourceType.FOOD, null);

        assertNotNull(resources);
        assertEquals(0, resources.size());
    }
}