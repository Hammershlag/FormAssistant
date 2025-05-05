package com.hammershlag.formassistantbackend.dto;

import com.hammershlag.formassistantbackend.models.SupportForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormUpdateRequest {

    private SupportForm form;
    private String userInput;
}
