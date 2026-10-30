package org.sid.transactionservice.web;
import lombok.RequiredArgsConstructor;
import org.sid.transactionservice.entites.Transaction;
import org.sid.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionRestController {
    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionRequest request) {
        return transactionService.createTransaction(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency(),
                request.getMerchantCategory(),
                request.getCountry()
        );
    }
}

@lombok.Data
class TransactionRequest {
    private UUID accountId;
    private BigDecimal amount;
    private String currency;
    private String merchantCategory;
    private String country;
}