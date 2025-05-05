package com.hammershlag.formassistantbackend.dto;

import com.hammershlag.formassistantbackend.models.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LLMResponse<T extends FormData> {
    private String message;
    private T updatedForm;
    private String formId;

    public LLMResponse(String message, T updatedForm) {
        this.message = message;
        this.updatedForm = updatedForm;
    }

}

