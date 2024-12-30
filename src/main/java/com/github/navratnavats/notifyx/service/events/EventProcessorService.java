package com.github.navratnavats.notifyx.service.events;

import com.github.navratnavats.notifyx.config.websocket.NotificationWebSocketHandler;
import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.NotificationEventDto;
import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.service.notification.NotificationDeliveryService;
import com.github.navratnavats.notifyx.service.notification.NotificationService;
import com.github.navratnavats.notifyx.service.preference.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public NotificationDto processEvent(NotificationEventDto notificationEventDto) {

        String eventType = notificationEventDto.getEventType();
        String userId = notificationEventDto.getUserId();
        String message = notificationEventDto.getMessage();
        List<String> channels = new ArrayList<>();

        log.info("Processing event: eventType={}, userId={}", eventType, userId);
        Optional<UserPreferencesDto> preferencesDtoOptional = preferenceService.getUserPreference(userId);

        if(notificationEventDto.getChannels() == null || notificationEventDto.getChannels().isEmpty()){
            if(preferencesDtoOptional.isPresent()){
                channels = preferencesDtoOptional.get().getPreferredChannels();
            }
            else {
                channels = preferenceService.createDefaultPreferences(userId);

            }
        }
        else {
            channels = notificationEventDto.getChannels();
            if(!preferencesDtoOptional.isPresent()) {
                saveUserPreference(channels, userId);
            }
        }


        if (preferencesDtoOptional.isPresent() && isMutedEvent(preferencesDtoOptional.get(), channels, userId)) {
            log.info("Event muted for user: userId={}, eventType={}", userId, eventType);
            return null;
        }

        log.info("Building notification for userId={}", userId);
        NotificationDto notificationDto = notificationService.buildNotification(eventType, userId, message, channels);

        log.info("Delivering notification for userId={}", userId);
        notificationDto.setDelivered(deliveryService.deliverNotification(notificationDto));

        log.info("Creating notification record for userId={}", userId);
        NotificationDto createdNotificationDTO = notificationService.createNotification(notificationDto);

        log.info("Event successfully processed for userId={}", userId);

        return createdNotificationDTO;
    }

    private void saveUserPreference(List<String> channels, String userId) {
        UserPreferencesDto preferencesDto = new UserPreferencesDto();
        preferencesDto.setPreferredChannels(channels);
        preferencesDto.setUserId(userId);
        preferencesDto.setMutedPreferences(List.of());
        preferenceService.savePreferences(preferencesDto);
    }

    private boolean isMutedEvent(UserPreferencesDto preferencesDto, List<String> channels, String userId) {
        int mutedChannels = 0;
        List<String> mutedPreferences = preferencesDto.getMutedPreferences();
        if (mutedPreferences != null){
            for(String channel : channels){
                if(mutedPreferences.contains(channel)) {
                    log.info("Muted channel detected: channel={}, userId={}", channel, userId);
                    mutedChannels++;
                }
            }
            if(mutedChannels == channels.size())
                return true;
            else{
                log.info("Removing muted channels");
                channels.removeAll(mutedPreferences);
                log.info("Channel list after muted channels {}", channels);
            }
        }
        return false;
    }
}
