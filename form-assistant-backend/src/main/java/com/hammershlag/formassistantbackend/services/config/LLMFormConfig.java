package com.hammershlag.formassistantbackend.services.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.models.FormData;
import com.hammershlag.formassistantbackend.storage.message.Message;

import java.io.IOException;
import java.util.List;

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
     * Retrieves the class type of the form data associated with this configuration.
     * This ensures that the correct form type is used during processing.
     *
     * @return the class type of the form data
     */
    Class<T> getFormClass();

    /**
     * Generates a formatted request string for the large language model (LLM) based on the provided context.
     * This includes previous messages, user input, and the current state of the form.
     *
     * @param previousMessages a list of previous messages exchanged in the conversation
     * @param userInput the user's current input to be processed
     * @param form the current state of the form being processed
     * @param objectMapper an instance of ObjectMapper for JSON serialization
     * @return a formatted request string ready to be sent to the LLM
     * @throws IOException if there is an error reading the configuration file or serializing the data
     */
    String getFormattedRequest(List<Message> previousMessages, String userInput, T form, ObjectMapper objectMapper) throws IOException;
}

