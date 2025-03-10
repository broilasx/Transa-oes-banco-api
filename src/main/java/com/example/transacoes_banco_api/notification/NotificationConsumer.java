package com.example.transacoes_banco_api.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.transacoes_banco_api.transaction.Transaction;

@Service
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
        .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
        .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "transacoes-banco-api")
    public void recieveNotification(Transaction transaction) {
        LOGGER.info("Notifying transaction {}", transaction);
        var response = restClient.get()
        .retrieve()
        .toEntity(Notification.class);

        if(response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificationException("Error sending notification");
        }
        LOGGER.info("Notification sent {}", response.getBody());
    }
}
