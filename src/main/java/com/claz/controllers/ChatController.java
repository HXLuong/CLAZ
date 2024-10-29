package com.claz.controllers;

import com.claz.models.ChatHistory;
import com.claz.services.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String userId, @RequestParam String message) {
        return chatService.getResponse(message, userId);
    }

    @GetMapping("/history/{userId}")
    public List<ChatHistory> getChatHistory(@PathVariable String userId) {
        return chatService.getChatHistory(userId).orElse(Collections.emptyList());
    }
}
