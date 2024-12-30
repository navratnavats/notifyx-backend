package com.github.navratnavats.notifyx.controller;

import com.github.navratnavats.notifyx.config.websocket.NotificationWebSocketHandler;
import com.github.navratnavats.notifyx.dto.NotificationDto;
import com.github.navratnavats.notifyx.dto.NotificationEventDto;
import com.github.navratnavats.notifyx.event.KafkaProducer;
import com.github.navratnavats.notifyx.service.events.EventProcessorService;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

@RestController
@RequestMapping("/api/v1/notification-delivery")
public class EventController {

    @Autowired
    private EventProcessorService eventProcessorService;

    @Autowired
    private NotificationWebSocketHandler notificationWebSocketHandler;

    @Autowired
    private KafkaProducer kafkaProducerService;

    @PostMapping("/send-kafka")
    public String sendNotification(@RequestBody NotificationEventDto eventDto) {
        kafkaProducerService.sendMessage(eventDto);
        return "Message sent successfully!";
    }

    @PostMapping("/send-notification")
    public ResponseEntity<?> notificationDelivery(@RequestBody NotificationEventDto notificationEventDto){
        String userId = notificationEventDto.getUserId();
        NotificationDto notificationDto = eventProcessorService.processEvent(notificationEventDto);


        return notificationDto == null ? new ResponseEntity<>("Event is muted, notification not sent", HttpStatus.OK) : new ResponseEntity<>(notificationDto, HttpStatus.CREATED);
    }
}
