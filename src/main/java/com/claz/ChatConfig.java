package com.claz;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Value("${openai.api.key}")
    private String apiKey;
        @Bean
        public ChatLanguageModel gpt3ChatModel() {
            return OpenAiChatModel.builder()
                    .apiKey(System.getenv(apiKey))
                    .modelName("gpt-3.5-turbo")
                    .build();
    }

}
