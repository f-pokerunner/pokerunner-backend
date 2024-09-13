package com.flab.pokerunner.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class LocationWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession webSocketSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.webSocketSession = session;
    }

    public void sendMessage(String message) throws IOException {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            webSocketSession.sendMessage(new TextMessage(message));
        }
    }
}