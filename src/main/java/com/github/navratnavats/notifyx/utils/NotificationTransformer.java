package com.github.navratnavats.notifyx.utils;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.model.Notification;
import com.github.navratnavats.notifyx.model.UserPreferences;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class NotificationTransformer {

    public static NotificationDto toNotificationDTO(Notification savedNotification) {

        return NotificationDto.builder()
                .id(savedNotification.getId())
                .delivered(savedNotification.isDelivered())
                .eventType(savedNotification.getEventType())
                .message(savedNotification.getMessage())
                .userId(savedNotification.getUserId())
                .channels(savedNotification.getChannels())
                .timeStamp(LocalDateTime.now()).build();
    }

    public static UserPreferencesDto toUserPreferencesDTO(UserPreferences userPreferences) {
        return UserPreferencesDto.builder()
                .preferredChannels(userPreferences.getPreferredChannels())
                .userId(userPreferences.getUserId())
                .mutedPreferences(userPreferences.getMutedPreferences())
                .build();
    }

    public static NotificationDto buildNotification(String eventType, String userId, String message, List<String> channels) {
        log.info("Building notification: eventType={}, userId={}", eventType, userId);
        String notificationId = "notifyx_" + UUID.randomUUID();

        NotificationDto notificationDto = NotificationDto.builder()
                .id(notificationId)
                .eventType(eventType)
                .message(message)
                .userId(userId)
                .channels(channels)
                .timeStamp(LocalDateTime.now()).build();
        log.info("Notification built: notificationId={}", notificationDto.getId());
        return notificationDto;
    }
}
