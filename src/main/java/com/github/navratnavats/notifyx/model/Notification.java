package com.github.navratnavats.notifyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    private String id;
    private String userId;
    private String message;
    private String eventType;
    private List<String> channels;
    private boolean delivered;
    private LocalDateTime timeStamp;

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


