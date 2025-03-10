package com.example.transacoes_banco_api;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

@EnableJdbcAuditing
@SpringBootApplication
public class TransacoesBancoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacoesBancoApiApplication.class, args);
	}

	NewTopic notificationTopic() {
		return TopicBuilder.name("transaction-notification")
		.build();
	}
}
