package com.github.navratnavats.notifyx.service.notification;

import com.github.navratnavats.notifyx.config.websocket.NotificationWebSocketHandler;
import com.github.navratnavats.notifyx.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationDeliveryService {

    @Autowired
    private NotificationWebSocketHandler notificationWebSocketHandler;

    public boolean deliverNotification(NotificationDto notificationDto) {
        log.info("Attempting to deliver notification: notificationId={}, userId={}", notificationDto.getId(), notificationDto.getUserId());
        if (notificationDto.getChannels().contains("WEBSOCKET") || notificationDto.getChannels().contains("WEB_PUSH")) {
            boolean delivered = notificationWebSocketHandler.sendNotification(notificationDto);
            log.info("Notification delivery status: notificationId={}, delivered={}", notificationDto.getId(), delivered);
            return delivered;
        }
        log.warn("No valid channels available for delivery: notificationId={}", notificationDto.getId());
        return false;
    }
}
