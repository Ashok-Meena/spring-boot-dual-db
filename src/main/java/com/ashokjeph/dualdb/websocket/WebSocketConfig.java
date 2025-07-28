package com.ashokjeph.dualdb.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("#{'${allowed.origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/squidSocket") // Your endpoint for client to connect
                .setAllowedOriginPatterns(allowedOrigins.toArray(new String[0])) // Allow CORS in dev
                .withSockJS(); // Optional: fallback for older browsers
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/Squid"); // prefix used in convertAndSend()
        config.setApplicationDestinationPrefixes("/app"); // for client to send
    }
}

