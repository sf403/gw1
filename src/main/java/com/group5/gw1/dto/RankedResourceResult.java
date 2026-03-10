package com.group5.gw1.dto;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RankedResource;
import com.group5.gw1.model.ResourceType;

import java.time.LocalDateTime;
import java.util.List;

public class RankedResourceResult {
    private List<RankedResource> rankedList;
    private Location workerLocation;
    private ResourceType requestedType;
    private LocalDateTime generatedAt;
}
