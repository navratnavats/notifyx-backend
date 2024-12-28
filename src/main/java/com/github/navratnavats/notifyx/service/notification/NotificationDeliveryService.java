package com.github.navratnavats.notifyx.service.notification;

import com.github.navratnavats.notifyx.config.websocket.NotificationWebSocketHandler;
import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeliveryService {

    @Autowired
    private NotificationWebSocketHandler notificationWebSocketHandler;

    public boolean deliverNotification(NotificationDto notificationDto){
        if (notificationDto.getChannels().contains("WEBSOCKET") || notificationDto.getChannels().contains("WEB_PUSH")){
            if(notificationWebSocketHandler.sendNotification(notificationDto)){
                return true;
            }
            else
                return false;
        }
    }
}
