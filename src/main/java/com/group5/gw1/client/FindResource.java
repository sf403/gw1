package com.group5.gw1.client;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.ResourceType;

import java.util.List;

public interface FindResource {
    public List<ResourceSource> findNearbyResource(ResourceType type, Location location);
}
