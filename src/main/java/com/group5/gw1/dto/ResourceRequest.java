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
}
