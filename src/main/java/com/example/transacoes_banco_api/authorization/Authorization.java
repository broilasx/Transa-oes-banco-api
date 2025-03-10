package com.example.transacoes_banco_api.authorization;

public record Authorization(
    String message
) {
    public boolean isAuthorized() {
        return message.equals("Autorizado");
    }
}
