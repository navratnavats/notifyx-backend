package com.github.navratnavats.notifyx.config.websocket;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = session.getPrincipal() != null ? session.getPrincipal().getName() : session.getId();
        activeSessions.put(userId, session);
        log.info("WebSocket connection established for user: {}", userId);
    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("Received message: {}", payload);
//
//        // Echo the message back to the client for testing
//        session.sendMessage(new TextMessage("Server received: " + payload));
//    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = session.getPrincipal() != null ? session.getPrincipal().getName() : session.getId();

        activeSessions.remove(userId);
        log.info("WebSocket connection closed for userId {}", userId);
    }

    public boolean sendNotification(NotificationDto notificationDto){
        String userId = notificationDto.getUserId();
        String message = notificationDto.getMessage();
        WebSocketSession webSocketSession = activeSessions.get(userId);
        if(webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.sendMessage(new TextMessage(message));
                log.info("Sent notification to user: {}", userId);
                return true;
            } catch (IOException e) {
                log.error("Exception thrown while sending message to userID {}", userId);
                throw new RuntimeException(e);
            }
        }
        else{
            log.warn("User {} is not connected to webScoket ", userId);
            return false;
        }
    }


}
