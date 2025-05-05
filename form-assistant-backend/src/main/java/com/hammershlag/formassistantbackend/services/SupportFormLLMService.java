package com.hammershlag.formassistantbackend.services;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import org.springframework.stereotype.Service;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Service
public class SupportFormLLMService {

    private final LLMService llmService;
    private final LLMFormConfig<SupportForm> config;

    public SupportFormLLMService(LLMService llmService, LLMFormConfig<SupportForm> config) {
        this.llmService = llmService;
        this.config = config;
    }

    public LLMResponse<SupportForm> updateSupportForm(SupportForm currentForm, String userInput) {
        if (currentForm == null) {
            currentForm = new SupportForm();
        }
        return llmService.generateFormContent(currentForm, userInput, config);
    }
}

