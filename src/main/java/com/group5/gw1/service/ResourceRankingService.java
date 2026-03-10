package com.group5.gw1.service;

import com.group5.gw1.dto.RankedResourceResult;
import com.group5.gw1.dto.ResourceRequest;

import java.util.List;

public interface ResourceRankingService {
    // TODO-6(Rostslav): create package similar to TODO-3 and implement this
    List<RankedResourceResult> getRankedResources(ResourceRequest request);
}
