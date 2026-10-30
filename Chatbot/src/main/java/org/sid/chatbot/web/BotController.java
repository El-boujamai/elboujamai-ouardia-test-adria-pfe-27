package org.sid.chatbot.web;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class BotController {
    private final ChatClient chatClient;

    public BotController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping
    public String chat(@RequestParam String message) {
        return chatClient.prompt(message).call().content();
    }
}