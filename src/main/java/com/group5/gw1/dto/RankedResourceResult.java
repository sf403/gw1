package com.group5.gw1.dto;

import com.group5.gw1.model.Location;
import com.group5.gw1.model.RankedResource;
import com.group5.gw1.model.ResourceType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RankedResourceResult {
    private List<RankedResource> rankedList;
    private Location workerLocation;
    private ResourceType requestedType;
    private String generatedAt;

    public RankedResourceResult(List<RankedResource> rankedList) {
        this.rankedList = rankedList;
        this.generatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public RankedResourceResult(List<RankedResource> rankedList, Location workerLocation, ResourceType requestedType) {
        this.rankedList = rankedList;
        this.workerLocation = workerLocation;
        this.requestedType = requestedType;
        this.generatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Getters
    public List<RankedResource> getRankedList() {
        return rankedList;
    }

    public Location getWorkerLocation() {
        return workerLocation;
    }

    public ResourceType getRequestedType() {
        return requestedType;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }
}
