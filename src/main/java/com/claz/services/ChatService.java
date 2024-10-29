//package com.claz.services;
//
//
//import com.claz.models.ChatHistory;
//import com.claz.repositories.ChatHistoryRepository;
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import dev.langchain4j.model.openai.OpenAiChatModel;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ChatService {
//
//    private final ChatLanguageModel chatModel;
//    private final ChatHistoryRepository chatHistoryRepository;
//
//    public ChatService(ChatHistoryRepository chatHistoryRepository,
//                       @Value("${OPENAI_API_KEY}") String apiKey,
//                       @Value("${langchain4j.open-ai.chat-model.model-name}") String modelName) {
//        this.chatModel = new OpenAiChatModel(apiKey, modelName);
//        this.chatHistoryRepository = chatHistoryRepository;
//    }
//
//    public String getResponse(String userMessage, String userId) {
//        // Gửi tin nhắn đến mô hình GPT và nhận phản hồi
//        String response = chatModel.chat(userMessage);
//
//        // Lưu lịch sử trò chuyện vào cơ sở dữ liệu
//        ChatHistory chatHistory = new ChatHistory(userId, userMessage, response);
//        chatHistoryRepository.save(chatHistory);
//
//        return response;
//    }
//
//    public Optional<ChatHistory> getChatHistory(String userId) {
//        // Lấy lịch sử trò chuyện của người dùng
//        return chatHistoryRepository.findById(Integer.valueOf(userId));
//    }
//}
