package com.group5.gw1.service;

import com.group5.gw1.model.ViabilityScore;
import com.group5.gw1.model.ResourceSource;

import java.util.List;

public interface ViabilityScoreService {
    // TODO-6(Rostslav): create an `impl` package in this current package, and
    //  write a class `ViabilityScoreServiceImpl` that implementes this
    //  interface. If any more model's are needed to implement this
    //  functionality then, accept those via input parameters.
    ViabilityScore calculateViabilityScore(ResourceSource resourceSource);
}
