package org.sid.notificationconsumer.service;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionConsumerService {

    @KafkaListener(topics = "bank.transactions.created", groupId = "bank-group")
    public void consume(ConsumerRecord<String, Object> record) {
        log.info("=== [KAFKA] Événement reçu ===");
        log.info("Clé (accountId) : {}", record.key());
        log.info("Payload : {}", record.value());
    }
}