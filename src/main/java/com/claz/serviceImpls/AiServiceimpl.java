package com.claz.serviceImpls;

import com.claz.services.AIService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiServiceimpl implements AIService {
    private final ChatLanguageModel gpt3ChatModel;
    public AiServiceimpl(ChatLanguageModel gpt3ChatModel) {
        this.gpt3ChatModel = gpt3ChatModel;
    }

    @Override
    public String chat(String message) {
        return gpt3ChatModel.chat(message).getContent();
    }
    }

