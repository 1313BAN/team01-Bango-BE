package com.ssafy.bango.global.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Value("${bango.ai.system-prompt}")
    String systemPrompt;

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem(systemPrompt).build();
    }
}
