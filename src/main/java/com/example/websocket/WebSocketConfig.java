package com.example.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * configureMessageBroker
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // enable a simple memory-based message broker
        // to carry the messages back to the client on destination
        // prefixed with "/topic"
        config.enableSimpleBroker("/topic", "/queue");

        // designate the "/app" prefix for messages that are bound for
        // @MessageMapping - annotated methods. This prefix will be used
        // to define all the message mappings. For example, "/app/ask"
        // is the endpoint that ChatBotController.getAnswer() method
        // is mapped to handle.
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * The registerStompEndpoints() method registers the "/stomp"
     * endpoint, enabling SockJS fallback options so that alternate transports
     * may be used if WebSocket is not available. The SockJS client will attempt
     * to connect to "/gs-guide-websocket" and use the best transport available
     * (websocket, xhr-streaming, xhr-polling, etc).
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatbot").setAllowedOrigins("*")
                .withSockJS();
    }
}
