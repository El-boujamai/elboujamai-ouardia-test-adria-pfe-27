package org.sid.accountservice.service;
import lombok.RequiredArgsConstructor;
import org.sid.accountservice.entites.Account;
import org.sid.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable : " + id));
    }

    public Account saveAccount(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID());
        }
        return accountRepository.save(account);
    }
}
