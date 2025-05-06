package com.hammershlag.formassistantbackend.controllers;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.form.SupportFormLLMService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/initial")
    public ResponseEntity<SupportForm> getInitialForm() {
        return ResponseEntity.ok(llmService.getInitialForm());
    }

    @SneakyThrows
    @PutMapping({"/{formId}", ""})
    public ResponseEntity<LLMResponse<SupportForm>> updateForm(@PathVariable(required = false) String formId, @RequestBody String userInput) {
        if (formId == null)
            formId = "";

        return ResponseEntity.ok(llmService.updateForm(formId, userInput));
    }
}

