package org.sid.transactionservice.clients;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountRestClient {

    @GetMapping("/api/accounts/{id}")
    @CircuitBreaker(name = "accountServiceCB", fallbackMethod = "getDefaultAccount")
    AccountDTO getAccountById(@PathVariable("id") UUID id);

    default AccountDTO getDefaultAccount(UUID id, Exception exception) {
        AccountDTO defaultAccount = new AccountDTO();
        defaultAccount.setId(id);
        defaultAccount.setStatus("BLOCKED");
        defaultAccount.setBalance(0.0);
        defaultAccount.setCurrency("EUR");
        return defaultAccount;
    }
}