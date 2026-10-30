package org.sid.chatbot.config;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("Vous êtes un assistant bancaire intelligent connecté aux micro-services.").build();
    }
}