package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ViabilityScore {
    private double totalScore;
    private double proximityScore;
    private double accessibilityScore;
    private double weatherSafetyScore;
}
