package com.hammershlag.formassistantbackend.services.config;

import com.hammershlag.formassistantbackend.models.SupportForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Configuration for the SupportForm used with the large language model (LLM).
 * Defines system instructions and generation configuration for form processing.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Component
public class SupportFormLLMConfig implements LLMFormConfig<SupportForm> {

    /**
     * Provides instructions for the LLM to process and update the support form.
     * Includes field constraints and guidance on responding to user queries.
     *
     * @return the system instruction text
     */
    @Override
    public String getSystemInstruction() {
        return """
        You are a helpful form assistant for filling out the following form. The form has these fields:
        - firstName: string, max 20 characters
        - lastName: string, max 20 characters
        - email: valid email format
        - reasonOfContact: string, max 100 characters
        - urgency: integer from 1 to 10 (or 0 if not specified)

        Your responsibilities:
        - Always return the complete JSON form with all fields.
        - If the user provides a value, update that field.
        - If a value is missing, fill it with "Unknown".
        - If the user has not provided required data, ask for it — one field at a time.
        - If the user asks to move on to the next field, continue with the next missing one.
        - If the user asks a question (e.g., "What about email?"), answer clearly, inferring context from the previous messages.
        - Do not modify the form unless the user explicitly provides or changes a value.
        - Always include a friendly message explaining what was updated or requested.

        Use the previous user messages as context to maintain a coherent and helpful conversation.
        """;
    }


    /**
     * Returns the configuration for generating form content.
     * Specifies response format and the form data structure.
     *
     * @return the LLM generation configuration
     */
    @Override
    public Map<String, Object> getGenerationConfig() {
        return Map.of(
                "response_mime_type", "application/json",
                "response_schema", Map.of(
                        "type", "OBJECT",
                        "properties", Map.of(
                                "updated_form", Map.of(
                                        "type", "OBJECT",
                                        "properties", Map.of(
                                                "firstName", Map.of("type", "STRING"),
                                                "lastName", Map.of("type", "STRING"),
                                                "email", Map.of("type", "STRING"),
                                                "reasonOfContact", Map.of("type", "STRING"),
                                                "urgency", Map.of("type", "INTEGER")
                                        ),
                                        "required", List.of("firstName", "lastName", "email", "reasonOfContact", "urgency")
                                ),
                                "message", Map.of(
                                        "type", "STRING",
                                        "description", "A friendly message to the user explaining what was updated."
                                )
                        )
                )
        );
    }

    /**
     * Returns the class type of the support form.
     * Ensures the correct form type is used in the process.
     *
     * @return the SupportForm class type
     */
    @Override
    public Class<SupportForm> getFormClass() {
        return SupportForm.class;
    }
}
