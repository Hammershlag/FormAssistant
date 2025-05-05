package com.hammershlag.formassistantbackend.services;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.FormData;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface LLMService {
    <T extends FormData> LLMResponse<T> generateFormContent(T form,
                                                            String userInput,
                                                            LLMFormConfig<T> config);
}

