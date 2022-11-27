package com.ohashi.strproducer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("str-topic", message).addCallback(
                success -> {
                    if (success != null) {
                        log.info(
                                "Send message with success {} to partition {}",
                                message,
                                success.getRecordMetadata().partition()
                        );
                    }
                },
                error -> log.error("Error send message")
        );
    }
}
