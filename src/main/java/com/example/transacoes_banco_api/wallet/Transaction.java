package com.example.transacoes_banco_api.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public record Transaction(
    @Id Long id,
    Long payer,
    Long payee,
    BigDecimal value,
    LocalDateTime createdAt
) {
    
    public Transaction{
        value = value.setScale(2);
    }
}
