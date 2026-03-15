package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResourceSource {
    private String sourceId;
    private String name;
    private ResourceType type;
    private Location position;
}