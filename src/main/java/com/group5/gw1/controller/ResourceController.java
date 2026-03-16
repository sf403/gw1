package com.group5.gw1.controller;

import com.group5.gw1.dto.RankedResourceResult;
import com.group5.gw1.dto.ResourceRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @PostMapping
    public RankedResourceResult getRankedResources(@RequestBody ResourceRequest request) {
        return resourceRankingService.findNearbyResources(request);
    }
}