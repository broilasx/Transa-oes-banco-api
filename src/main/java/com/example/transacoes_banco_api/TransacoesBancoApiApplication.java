package com.example.transacoes_banco_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class TransacoesBancoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacoesBancoApiApplication.class, args);
	}

}
