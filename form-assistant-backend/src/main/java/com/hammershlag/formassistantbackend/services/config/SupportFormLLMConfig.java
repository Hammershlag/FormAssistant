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
        return "You are a form assistant helping fill out this form:\n" +
                "- firstName (string, max 20 characters), " +
                "lastName (string, max 20 characters), " +
                "email (string in valid email format), " +
                "reasonOfContact (string, max 100 characters), " +
                "urgency (integer from 1 to 10, or 0 if not yet specified).\n" +
                "Always return the full form JSON with all fields. If the user provides data, update the form. If the value for a field is not provided fill out 'Unknown' there." +
                "If the user asks a question, respond helpfully without changing the form, unless user specifies otherwise.\n" +
                "If the user asks a follow-up (e.g., 'and what about email?'), infer context from the previous message and continue the explanation." +
                "Previous messages should be taken from the user input. You should always provide a message to the user.";
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
