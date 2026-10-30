package org.sid.chatbot.tools;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BankingTools {
    private final RestTemplate restTemplate = new RestTemplate();

    @Tool(description = "Récupérer les informations d'un compte bancaire")
    public String getAccount(String accountId) {
        return restTemplate.getForObject("http://account-service/api/accounts/" + accountId, String.class);
    }
}