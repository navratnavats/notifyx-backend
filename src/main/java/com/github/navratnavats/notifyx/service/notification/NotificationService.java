package com.github.navratnavats.notifyx.service.notification;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.model.Notification;
import com.github.navratnavats.notifyx.repository.NotificationRepository;
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
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PreferenceService preferenceService;

    public NotificationDto createNotification(NotificationDto notificationDto) {
        log.info("Saving notification: userId={}, eventType={}", notificationDto.getUserId(), notificationDto.getEventType());
        Notification notification = new Notification();
        notification.setId(notification.getId());
        notification.setDelivered(notificationDto.isDelivered());
        notification.setChannels(notificationDto.getChannels());
        notification.setMessage(notificationDto.getMessage());
        notification.setTimeStamp(notificationDto.getTimeStamp());
        notification.setEventType(notificationDto.getEventType());
        notification.setUserId(notificationDto.getUserId());

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification saved: notificationId={}", savedNotification.getId());
        return toDTO(savedNotification);
    }

    public NotificationDto buildNotification(String eventType, String userId, String message, Optional<UserPreferencesDto> preferencesDtoOptional) {
        log.info("Building notification: eventType={}, userId={}", eventType, userId);
        String notificationId = "notifyx_" + UUID.randomUUID();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notificationId);
        notificationDto.setEventType(eventType);
        notificationDto.setMessage(message);
        notificationDto.setUserId(userId);

        if (preferencesDtoOptional.isPresent()) {
            UserPreferencesDto preferencesDto = preferencesDtoOptional.get();
            notificationDto.setChannels(preferencesDto.getPreferredChannels());
        } else {
            log.info("No user preferences found, creating default preferences for userId={}", userId);
            createDefaultPreferences(userId);
            notificationDto.setChannels(Arrays.asList("WEB_PUSH", "WEBSOCKET", "EMAIL"));
        }

        notificationDto.setTimeStamp(LocalDateTime.now());
        log.info("Notification built: notificationId={}", notificationDto.getId());
        return notificationDto;
    }

    private void createDefaultPreferences(String userId) {
        UserPreferencesDto defaultUserPreference = new UserPreferencesDto();
        defaultUserPreference.setId(userId);
        defaultUserPreference.setMutedPreferences(null);
        defaultUserPreference.setPreferredChannels(Arrays.asList("WEB_PUSH", "WEBSOCKET", "EMAIL"));

        preferenceService.savePreferences(defaultUserPreference);
        log.info("Default preferences created for userId={}", userId);
    }

    private NotificationDto toDTO(Notification savedNotification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setUserId(savedNotification.getUserId());
        notificationDto.setDelivered(savedNotification.isDelivered());
        notificationDto.setMessage(savedNotification.getMessage());
        notificationDto.setChannels(savedNotification.getChannels());
        notificationDto.setEventType(savedNotification.getEventType());
        notificationDto.setTimeStamp(savedNotification.getTimeStamp());

        return notificationDto;
    }
}