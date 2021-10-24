package com.controller.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(myHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor());

        webSocketHandlerRegistry
                .addHandler(myHandler(), "/message")
                .addHandler(myHandler(), "/signal")
                .addInterceptors(new HttpSessionHandshakeInterceptor());


    }

    @Bean
    @Scope(scopeName = "prototype")
    public WebSocketHandler myHandler(){
        return new MyHandler();
    }
}