package com.hammershlag.formassistantbackend.controllers;

import com.hammershlag.formassistantbackend.dto.FormUpdateRequest;
import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.SupportFormLLMService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@RestController
@RequestMapping("/support-form")
public class SupportFormController {

    private final SupportFormLLMService llmService;

    public SupportFormController(SupportFormLLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/update")
    public LLMResponse<SupportForm> updateForm(@RequestBody FormUpdateRequest request) {
        return llmService.updateSupportForm(request.getFormId(), request.getUserInput());
    }
}

