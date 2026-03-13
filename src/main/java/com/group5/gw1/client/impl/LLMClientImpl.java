package com.group5.gw1.client.impl;

import com.group5.gw1.client.LLMClient;
import com.group5.gw1.model.LocalizedAdvice;
import com.group5.gw1.model.Location;
import com.group5.gw1.model.TechnicalAlerts;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LLMClientImpl implements LLMClient {

    private final ChatClient chatClient;

    public LLMClientImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public LocalizedAdvice summarizeAlerts(
        TechnicalAlerts alerts,
        Location location
    ) {
        String prompt = """
            Summarize the following technical alerts into short sentence and translate it to
            correct language and dialect according to location: %s

            Technical alert: %s
            Severity: %s
            """.formatted(
                location.toString(),
                alerts.getTechnicalMessage(),
                alerts.getSeverity()
            );

        LocalizedAdvice response = chatClient
            .prompt()
            .user(prompt)
            .call()
            .entity(LocalizedAdvice.class);
        return response;
    }
}
