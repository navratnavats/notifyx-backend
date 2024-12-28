package com.github.navratnavats.notifyx.service.events;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.service.notification.NotificationDeliveryService;
import com.github.navratnavats.notifyx.service.notification.NotificationService;
import com.github.navratnavats.notifyx.service.preference.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        log.info("Processing event: eventType={}, userId={}", eventType, userId);
        Optional<UserPreferencesDto> preferencesDtoOptional = preferenceService.getUserPreference(userId);
        if (preferencesDtoOptional.isPresent() && isMutedEvent(preferencesDtoOptional.get(), eventType, userId)) {
            log.info("Event muted for user: userId={}, eventType={}", userId, eventType);
            return;
        }

        log.info("Building notification for userId={}", userId);
        NotificationDto notificationDto = notificationService.buildNotification(eventType, userId, message, preferencesDtoOptional);

        log.info("Delivering notification for userId={}", userId);
        notificationDto.setDelivered(!deliveryService.deliverNotification(notificationDto));

        log.info("Creating notification record for userId={}", userId);
        NotificationDto createdNotificationDTO = notificationService.createNotification(notificationDto);

        log.info("Event successfully processed for userId={}", userId);
    }

    private boolean isMutedEvent(UserPreferencesDto preferencesDto, String eventType, String userId) {
        if (preferencesDto.getMutedPreferences().contains(eventType)) {
            log.info("Muted event detected: eventType={}, userId={}", eventType, userId);
            return true;
        }
        return false;
    }
}
