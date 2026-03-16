package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

@Getter
@AllArgsConstructor
public class ResourceSource implements Comparable {
    private String sourceId;
    private String name;
    private ResourceType type;
    private Location position;

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
