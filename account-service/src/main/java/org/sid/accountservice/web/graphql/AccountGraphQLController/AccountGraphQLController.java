package org.sid.accountservice.web.graphql.AccountGraphQLController;
import lombok.RequiredArgsConstructor;
import org.sid.accountservice.entites.Account;
import org.sid.accountservice.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountGraphQLController {
    private final AccountService accountService;

    @QueryMapping
    public List<Account> allAccounts() {
        return accountService.getAllAccounts();
    }

    @QueryMapping
    public Account accountById(@Argument UUID id) {
        return accountService.getAccountById(id);
    }
}