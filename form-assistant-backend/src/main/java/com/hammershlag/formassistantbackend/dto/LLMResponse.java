package com.hammershlag.formassistantbackend.dto;

import com.hammershlag.formassistantbackend.models.FormData;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public class LLMResponse<T extends FormData> {
    private String message;
    private T updatedForm;

    public LLMResponse(String message, T updatedForm) {
        this.message = message;
        this.updatedForm = updatedForm;
    }

    public String getMessage() {
        return message;
    }

    public T getUpdatedForm() {
        return updatedForm;
    }
}

