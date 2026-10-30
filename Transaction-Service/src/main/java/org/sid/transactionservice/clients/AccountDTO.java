package org.sid.transactionservice.clients;
import lombok.Data;
import java.util.UUID;

@Data
public class AccountDTO {
    private UUID id;
    private String customerReference;
    private String status;
    private String currency;
    private double balance;
}