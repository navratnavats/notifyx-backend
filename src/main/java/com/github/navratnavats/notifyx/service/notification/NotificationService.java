package com.github.navratnavats.notifyx.service.notification;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.model.Notification;
import com.github.navratnavats.notifyx.repository.NotificationRepository;
import com.github.navratnavats.notifyx.service.preference.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        notification.setId(notificationDto.getId());
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

    public NotificationDto buildNotification(String eventType, String userId, String message, List<String> channels) {
        log.info("Building notification: eventType={}, userId={}", eventType, userId);
        String notificationId = "notifyx_" + UUID.randomUUID();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notificationId);
        notificationDto.setEventType(eventType);
        notificationDto.setMessage(message);
        notificationDto.setUserId(userId);
        notificationDto.setChannels(channels);

        notificationDto.setTimeStamp(LocalDateTime.now());
        log.info("Notification built: notificationId={}", notificationDto.getId());
        return notificationDto;
    }



    public List<NotificationDto> getNotificationsForUser(String userID){
        List<Notification> notification = notificationRepository.findByUserId();

        return notification.stream().map(this::toDTO).collect(Collectors.toList());
    }


    private NotificationDto toDTO(Notification savedNotification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(savedNotification.getId());
        notificationDto.setUserId(savedNotification.getUserId());
        notificationDto.setDelivered(savedNotification.isDelivered());
        notificationDto.setMessage(savedNotification.getMessage());
        notificationDto.setChannels(savedNotification.getChannels());
        notificationDto.setEventType(savedNotification.getEventType());
        notificationDto.setTimeStamp(savedNotification.getTimeStamp());

        return notificationDto;
    }
}
