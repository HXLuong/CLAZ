//package com.claz.serviceImpls;
//
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import dev.langchain4j.model.openai.OpenAiChatModel;
//import org.springframework.stereotype.Service;
//@Service
//public class AssistantIplm implements Assistant{
//
//    private final ChatLanguageModel chatModel;
//
//    public AssistantImpl() {
//        this.chatModel = OpenAiChatModel.builder()
//                .apiKey("YOUR_OPENAI_API_KEY") // Thay bằng API Key thực tế của bạn
//                .build();
//    }
//
//    public AssistantIplm(ChatLanguageModel chatModel) {
//        this.chatModel = chatModel;
//    }
//
//    @Override
//    public String getResponse(String userMessage) {
//        // Gọi phương thức generateMessage để lấy phản hồi từ ChatGPT
//        return chatModel.chat(userMessage).getContent();
//    }
//}
