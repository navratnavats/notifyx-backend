package com.github.navratnavats.notifyx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationDto {

    private final String id;
    private final String userId;
    private final String message;
    private final String eventType;
    private final List<String> channels;
    private final boolean delivered;
    private final LocalDateTime timeStamp;

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", eventType='" + eventType + '\'' +
                ", channels=" + channels +
                ", delivered=" + delivered +
                ", timeStamp=" + timeStamp +
                '}';
    }
}


