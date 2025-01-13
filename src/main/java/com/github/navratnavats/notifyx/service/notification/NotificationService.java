package com.github.navratnavats.notifyx.service.notification;

import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.model.Notification;
import com.github.navratnavats.notifyx.repository.NotificationRepository;
import com.github.navratnavats.notifyx.utils.NotificationTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.navratnavats.notifyx.utils.NotificationTransformer.toNotificationDTO;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationDto createNotification(NotificationDto notificationDto, boolean isDelivered) {
        log.info("Saving notification: userId={}, eventType={}", notificationDto.getUserId(), notificationDto.getEventType());
        Notification notification = Notification.builder()
                .id(notificationDto.getId())
                .delivered(isDelivered)
                .channels(notificationDto.getChannels())
                .message(notificationDto.getMessage())
                .timeStamp(notificationDto.getTimeStamp())
                .eventType(notificationDto.getEventType())
                .userId(notificationDto.getUserId())
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification saved: notificationId={}", savedNotification.getId());
        return toNotificationDTO(savedNotification);
    }

    public List<NotificationDto> getNotificationsForUser(String userID) {
        List<Notification> notification = notificationRepository.findByUserId(userID);

       return notification.stream().map(NotificationTransformer::toNotificationDTO).collect(Collectors.toList());

    }

}
