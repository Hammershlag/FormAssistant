package com.hammershlag.formassistantbackend.services.config;

import com.hammershlag.formassistantbackend.models.FormData;

import java.util.Map;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface LLMFormConfig<T extends FormData> {
    String getSystemInstruction();
    Map<String, Object> getGenerationConfig();
    Class<T> getFormClass();
}
