package com.hammershlag.formassistantbackend.services;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import com.hammershlag.formassistantbackend.storage.FormStorage;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Service
public class SupportFormLLMService {

    private final LLMService llmService;
    private final LLMFormConfig<SupportForm> config;
    private final FormStorage formStorage;

    public SupportFormLLMService(LLMService llmService,
                                 LLMFormConfig<SupportForm> config,
                                 FormStorage formStorage) {
        this.llmService = llmService;
        this.config = config;
        this.formStorage = formStorage;
    }

    @SneakyThrows
    public LLMResponse<SupportForm> updateSupportForm(String formId, String userInput) {

        SupportForm form;
        Optional<String> formStrOpt = formStorage.getForm(formId);
        if (formStrOpt.isEmpty()) {
            form = new SupportForm();
            formId = formStorage.saveForm(form.toJson());
        } else {
            String formStr = formStrOpt.get();
            form = SupportForm.fromJson(formStr);
        }


        LLMResponse<SupportForm> response = llmService.generateFormContent(form, userInput, config);
        response.getUpdatedForm().isDataValid();
        response.getUpdatedForm().normalizeData();
        formStorage.updateForm(formId, response.getUpdatedForm().toJson());
        response.setFormId(formId);

        return response;
    }

    public SupportForm getInitialForm() {
        return new SupportForm();
    }

}


