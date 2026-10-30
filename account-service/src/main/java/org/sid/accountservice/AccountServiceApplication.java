package org.sid.accountservice;

import org.sid.accountservice.enume.AccountStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.sid.accountservice.entites.Account;
import org.sid.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountRepository accountRepository) {
        return args -> {
            // Création de quelques comptes de test
            Account acc1 = Account.builder()
                    .id(UUID.randomUUID())
                    .customerReference("CUST-001")
                    .status(AccountStatus.ACTIVE)
                    .currency("EUR")
                    .balance(5000.0)
                    .build();

            Account acc2 = Account.builder()
                    .id(UUID.randomUUID())
                    .customerReference("CUST-002")
                    .status(AccountStatus.ACTIVE)
                    .currency("EUR")
                    .balance(12500.50)
                    .build();

            Account acc3 = Account.builder()
                    .id(UUID.randomUUID())
                    .customerReference("CUST-003")
                    .status(AccountStatus.BLOCKED)
                    .currency("USD")
                    .balance(300.0)
                    .build();

            accountRepository.save(acc1);
            accountRepository.save(acc2);
            accountRepository.save(acc3);

            // Affichage dans la console pour vérification
            accountRepository.findAll().forEach(acc -> {
                System.out.println("Compte créé -> ID: " + acc.getId() + " | Client: " + acc.getCustomerReference() + " | Solde: " + acc.getBalance() + " " + acc.getCurrency());
            });
        };
    }
}
