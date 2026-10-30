package org.sid.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.sid.transactionservice.clients.AccountDTO;
import org.sid.transactionservice.clients.AccountRestClient;
import org.sid.transactionservice.entites.Transaction;
import org.sid.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRestClient accountRestClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${bank.transaction.max-amount:1000}")
    private double maxAmount;

    public Transaction createTransaction(UUID accountId, BigDecimal amount, String currency, String merchantCategory, String country) {
        // 1. Vérification du montant strictement positif et respect du plafond
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || amount.doubleValue() > maxAmount) {
            throw new IllegalArgumentException("Montant invalide ou dépassement du plafond autorisé (" + maxAmount + ")");
        }

        // 2. Consultation du compte via OpenFeign (avec disjoncteur Resilience4J)
        AccountDTO account = accountRestClient.getAccountById(accountId);
        if (account == null || !"ACTIVE".equals(account.getStatus())) {
            throw new IllegalStateException("Le compte n'existe pas ou n'est pas actif.");
        }

        // 3. Vérification de la devise
        if (!account.getCurrency().equalsIgnoreCase(currency)) {
            throw new IllegalArgumentException("La devise de la transaction ne correspond pas à celle du compte.");
        }

        // 4. Enregistrement de la transaction
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(amount)
                .currency(currency)
                .merchantCategory(merchantCategory)
                .country(country)
                .createdAt(Instant.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        // 5. Publication de l'événement dans Kafka (Partie F)
        kafkaTemplate.send("bank.transactions.created", accountId.toString(), savedTransaction);

        return savedTransaction;
    }
}
