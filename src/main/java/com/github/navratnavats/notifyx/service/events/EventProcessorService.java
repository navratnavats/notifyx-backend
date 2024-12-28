package com.github.navratnavats.notifyx.service.events;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.model.Notification;
import com.github.navratnavats.notifyx.service.notification.NotificationDeliveryService;
import com.github.navratnavats.notifyx.service.notification.NotificationService;
import com.github.navratnavats.notifyx.service.preference.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EventProcessorService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationDeliveryService deliveryService;

    @Autowired
    private PreferenceService preferenceService;

    public void processEvent(String eventType, String userId, String message) {
        Optional<UserPreferencesDto> preferencesDtoOptional = preferenceService.getUserPreference(userId);
        if (preferencesDtoOptional.isPresent() && isMutedEvent(preferencesDtoOptional.get(), eventType, userId)) {
            return;
        }

        NotificationDto notificationDto = notificationService.buildNotification(eventType, userId, message, preferencesDtoOptional);

        notificationDto.setDelivered(!deliveryService.deliverNotification(notificationDto));

        NotificationDto createdNotificationDTO = notificationService.createNotification(notificationDto);

        log.info("Event Processed for userID {}", userId);
    }


    private boolean isMutedEvent(UserPreferencesDto preferencesDto, String eventType, String userId) {
        if (preferencesDto.getMutedPreferences().contains(eventType)) {
            log.info("Event Type {} is a muted preference {} for user {}", eventType, preferencesDto.getMutedPreferences(), userId);
            return true;
        }
        return false;
    }


}