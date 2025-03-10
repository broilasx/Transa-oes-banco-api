package com.example.transacoes_banco_api.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transacoes_banco_api.authorization.AuthorizeService;
import com.example.transacoes_banco_api.notification.NotificationService;
import com.example.transacoes_banco_api.wallet.Wallet;
import com.example.transacoes_banco_api.wallet.WalletRepository;
import com.example.transacoes_banco_api.wallet.WalletType;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizeService authorizeService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizeService authorizeService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizeService = authorizeService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        //1 - validar
        validate(transaction);
        //2 - criar transacao
        var newTransaction = transactionRepository.save(transaction);
        //3 - retirar valor da carteira
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));
        //4 - chamar servicos externos
        authorizeService.authorize(transaction);
        //5 - notificar
        notificationService.notify(transaction);
        return newTransaction;
    }
        
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
        .map(payee -> walletRepository.findById(transaction.payer())
            .map(
                payer -> isTransactionValid(transaction, payer) ? true : null)
            .orElseThrow(() -> new InvalidTransactionException(
                "Invalid transaction - " + transaction)))
        .orElseThrow(() -> new InvalidTransactionException(
            "Invalid transaction - " + transaction));
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() &&
            payer.balance().compareTo(transaction.value()) >= 0 &&
            !payer.id().equals(transaction.payee());
    }
}

