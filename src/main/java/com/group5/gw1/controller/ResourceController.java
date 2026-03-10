package com.group5.gw1.controller;

import com.group5.gw1.dto.RankedResourceResult;
import com.group5.gw1.dto.ResourceRequest;
import com.group5.gw1.service.ResourceRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @Autowired
    private ResourceRankingService resourceRankingService;

    // TODO-2: this is the send main point of entry of the application after the `main` method in `Gw1Application`
    //      this is where we start to follow the sequence diagram.
    @PostMapping
    public RankedResourceResult getRankedResources(@RequestBody ResourceRequest request) {
        throw new UnsupportedOperationException("Unimplemented");
    }
}
