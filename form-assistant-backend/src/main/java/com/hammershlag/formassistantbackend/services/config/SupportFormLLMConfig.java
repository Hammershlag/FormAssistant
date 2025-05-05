package com.hammershlag.formassistantbackend.services.config;

import com.hammershlag.formassistantbackend.models.SupportForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */

@Component
public class SupportFormLLMConfig implements LLMFormConfig<SupportForm> {

    @Override
    public String getSystemInstruction() {
        return "You are a form-filling assistant. Process user inputs to update the given form JSON. " +
                "Ensure all fields meet the following constraints: Firstname and Lastname (strings, max 20 characters), " +
                "Email (valid email format), Reason of contact (string, max 100 characters), Urgency (integer, 0–10, " +
                "where 0 means the user has not specified it yet). Always return the complete form with all fields, " +
                "even if some values remain empty or unchanged. Respond with the updated form and a friendly message " +
                "explaining what was updated.";
    }

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
                                                "firstName", Map.of("type", "STRING", "maxLength", 20),
                                                "lastName", Map.of("type", "STRING", "maxLength", 20),
                                                "email", Map.of("type", "STRING", "maxLength", 64),
                                                "reasonOfContact", Map.of("type", "STRING", "maxLength", 100),
                                                "urgency", Map.of("type", "INTEGER", "minimum", 0, "maximum", 10)
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

    @Override
    public Class<SupportForm> getFormClass() {
        return SupportForm.class;
    }
}
