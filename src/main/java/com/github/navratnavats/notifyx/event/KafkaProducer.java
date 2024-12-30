package com.github.navratnavats.notifyx.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

    @Value("${kafka.topic.send.notification}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        log.info("Sending message to topic {}: {}", TOPIC, message);
        kafkaTemplate.send(TOPIC, message);
        log.info("Message sent successfully to topic {}", TOPIC);
    }
}
