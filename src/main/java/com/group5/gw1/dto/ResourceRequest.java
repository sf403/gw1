package com.group5.gw1.dto;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceType;

/*
 * This DTO represents the initial request made by the user to query the `type`
 * of resource, around their current `location`
 */
public class ResourceRequest {
    private ResourceType type;
    private Location location;

    // Getters
    public ResourceType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    // Setters (for JSON deserialization)
    public void setType(ResourceType type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
