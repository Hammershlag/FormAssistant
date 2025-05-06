package com.hammershlag.formassistantbackend.services.provider;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.FormData;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import com.hammershlag.formassistantbackend.storage.message.Message;

import java.util.List;

/**
 * Service for generating form content using a large language model (LLM).
 * It processes form data and user input, incorporating previous conversation history.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface LLMService {

    /**
     * Generates form content based on the provided form, previous messages, and user input.
     * It uses the specified configuration for the form type.
     *
     * @param form the form data to be processed
     * @param previousMessages list of previous conversation messages
     * @param userInput the new user input to be incorporated
     * @param config the configuration specific to the form
     * @param <T> the type of the form data
     * @return the generated LLM response containing the updated form and a message
     */
    <T extends FormData> LLMResponse<T> generateFormContent(T form,
                                                            List<Message> previousMessages,
                                                            String userInput,
                                                            LLMFormConfig<T> config);
}


