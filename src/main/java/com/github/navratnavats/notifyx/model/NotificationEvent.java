package com.github.navratnavats.notifyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NotificationEvent {
    private String userId;
    private String eventType;
    private List<String> channels;
    private String message;
    private Map<String, Object> metadata;

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "userId='" + userId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", channels=" + channels +
                ", message='" + message + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}

