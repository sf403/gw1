package com.group5.gw1.client;

import com.group5.gw1.model.LocalizedAdvice;
import com.group5.gw1.model.TechnicalAlerts;

public interface LLMClient {
    LocalizedAdvice summarizeAlerts(TechnicalAlerts alerts);
}
