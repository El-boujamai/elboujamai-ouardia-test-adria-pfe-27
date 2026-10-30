package org.sid.accountservice.entites;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id
    private UUID id;
    private String customerReference;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private double balance;
}

enum AccountStatus {
    ACTIVE, BLOCKED
}