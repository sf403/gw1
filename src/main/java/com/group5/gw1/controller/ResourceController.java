package com.group5.gw1.controller;

import com.group5.gw1.dto.RankedResourceResult;
import com.group5.gw1.dto.ResourceRequest;
import com.group5.gw1.client.GiveFookUkClient;
import com.group5.gw1.model.ResourceSource;
import com.group5.gw1.model.RankedResource;
import com.group5.gw1.model.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private GiveFookUkClient giveFoodUkClient;

    // TODO-2(Faizan): this is the send main point of entry of the application after the `main` method in
    //  `Gw1Application` this is where we start to follow the sequence diagram.
    @PostMapping
    public RankedResourceResult getRankedResources(@RequestBody ResourceRequest request) {
        try {
            if (request.getType() == ResourceType.FOOD) {
                List<ResourceSource> resources = giveFoodUkClient.findNearbyResource(ResourceType.FOOD, request.getLocation());
                List<RankedResource> rankedResources = new ArrayList<>();
                for (int i = 0; i < resources.size(); i++) {
                    ResourceSource resource = resources.get(i);
                    rankedResources.add(new RankedResource(i + 1, resource, 1.0, 1.0, 1.0, 1.0));
                }
                LOG.info("Found {} food resources", resources.size());
                return new RankedResourceResult(rankedResources, request.getLocation(), request.getType());
            }
            return new RankedResourceResult(List.of());
        } catch (Exception e) {
            LOG.error("Error processing resource request", e);
            throw e;
        }
    }
}
