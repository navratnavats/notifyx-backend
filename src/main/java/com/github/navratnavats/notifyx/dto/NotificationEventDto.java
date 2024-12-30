package com.github.navratnavats.notifyx.dto;

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
public class NotificationEventDto {
    private String userId;
    private String eventType;
    private String message;
    private List<String> channels;
    private Map<String, Object> metadata;

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "userID='" + userId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", message='" + message + '\'' +
                ", metadata=" + metadata + '\''+
                ", channel=" + channels +
                '}';
    }
}

