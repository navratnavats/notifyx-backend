package com.github.navratnavats.notifyx.event;

import com.github.navratnavats.notifyx.dto.NotificationEventDto;
import com.github.navratnavats.notifyx.service.events.EventProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {


    @Autowired
    private EventProcessorService processorService;

    @KafkaListener(topics = "${kafka.topic.send.notification}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(NotificationEventDto event) {
        log.info("Consumed event: {}", event);
        try {
            processorService.processEvent(event);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
