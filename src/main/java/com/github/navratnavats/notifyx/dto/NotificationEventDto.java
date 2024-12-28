package com.github.navratnavats.notifyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NotificationEventDto {
    private String userID;
    private String eventType;
    private String message;
    private Map<String, Object> metadata;

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "userID='" + userID + '\'' +
                ", eventType='" + eventType + '\'' +
                ", message='" + message + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}

