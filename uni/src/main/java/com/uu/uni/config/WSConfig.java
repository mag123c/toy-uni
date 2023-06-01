package com.uu.uni.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.uu.uni.chat.handler.MainChatting;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WSConfig implements WebSocketConfigurer{

    private final MainChatting mainChatting;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(mainChatting, "/mainchatting").setAllowedOriginPatterns("*").withSockJS();		
	}
	
}
