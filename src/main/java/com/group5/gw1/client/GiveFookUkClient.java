package com.group5.gw1.client;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.ResourceType;

import java.util.List;

public interface GiveFookUkClient {
    // TODO-3(Abhiram): refer `https://www.givefood.org.uk/api` and make `GiveFoodUkClientImpl` class that implements
    //  this interface in a new package called `impl` in this (`client`) package.
    public List<ResourceSource> findNearbyResource(ResourceType type, Location location);
}
