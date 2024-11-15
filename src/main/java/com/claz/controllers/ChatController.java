package com.claz.controllers;
import com.claz.services.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/message")
    public ResponseEntity<String> getChatResponse(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        String reply = aiService.chat(userMessage); // Gọi hàm assistant.chat để nhận phản hồi
        return ResponseEntity.ok(reply);
    }
}

