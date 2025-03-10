package com.example.transacoes_banco_api.authorization;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizeService {
    public RestClient restClient;

    public AuthorizeService(RestClient.Builder builder) {
        this.restClient = builder
        .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
        .build();
    }
    public void authorize(com.example.transacoes_banco_api.transaction.Transaction transaction) {
        var response = restClient.get()
        .retrieve()
        .toEntity(Authorization.class);

        if(response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new UnauthorizedTransactionException("Unauthorized transaction");
        }
    }

}
