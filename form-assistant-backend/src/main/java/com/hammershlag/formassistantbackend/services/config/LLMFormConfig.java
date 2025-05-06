package com.hammershlag.formassistantbackend.services.config;

import com.hammershlag.formassistantbackend.models.FormData;

import java.util.Map;

/**
 * Configuration interface for generating content with a large language model (LLM) for a specific form.
 *
 * @param <T> the type of form data the configuration applies to
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface LLMFormConfig<T extends FormData> {

    /**
     * Returns the system instruction for the LLM to follow when processing the form.
     *
     * @return the system instruction text
     */
    String getSystemInstruction();

    /**
     * Returns the configuration for generating content, including response format and constraints.
     *
     * @return the LLM generation configuration
     */
    Map<String, Object> getGenerationConfig();

    /**
     * Returns the class type of the form data to ensure correct handling of form structure.
     *
     * @return the form data class type
     */
    Class<T> getFormClass();
}

