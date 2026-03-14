package com.group5.gw1.model;

public class ResourceSource {
    private String sourceId;
    private String name;
    private ResourceType type;
    private Location position;

    public ResourceSource(String sourceId, String name, ResourceType type, Location position) {
        this.sourceId = sourceId;
        this.name = name;
        this.type = type;
        this.position = position;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public Location getPosition() {
        return position;
    }
}
