package com.group5.gw1.model;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ViabilityScore {
    private double totalScore;
    private double proximityScore;
    private double accessibilityScore;
    private double weatherSafetyScore;
}
