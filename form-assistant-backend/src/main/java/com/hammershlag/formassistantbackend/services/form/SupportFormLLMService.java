package com.hammershlag.formassistantbackend.services.form;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.provider.LLMService;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import com.hammershlag.formassistantbackend.storage.form.FormStorage;
import com.hammershlag.formassistantbackend.storage.message.MessageHistoryStorage;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to SupportForm using LLM (Large Language Model).
 * This service interacts with the LLM to update form content, retrieve previous messages, and manage form storage.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Service
public class SupportFormLLMService implements FormLLMService<SupportForm> {

    private final LLMService llmService;
    private final LLMFormConfig<SupportForm> config;
    private final FormStorage formStorage;
    private final MessageHistoryStorage messageHistoryStorage;

    public SupportFormLLMService(LLMService llmService,
                                 LLMFormConfig<SupportForm> config,
                                 FormStorage formStorage,
                                 MessageHistoryStorage messageHistoryStorage) {
        this.llmService = llmService;
        this.config = config;
        this.formStorage = formStorage;
        this.messageHistoryStorage = messageHistoryStorage;
    }

    /**
     * Updates the SupportForm based on the provided user input.
     * If the form does not exist, a new form is created. The method also retrieves
     * previous messages and uses the LLM to generate updated form content.
     *
     * @param formId the ID of the existing form
     * @param userInput the user input that will be used to update the form
     * @return an LLMResponse containing the updated form and a message explaining the updates
     * @throws Exception if an error occurs during the update process
     */
    @SneakyThrows
    @Override
    public LLMResponse<SupportForm> updateForm(String formId, String userInput) {

        SupportForm form;
        Optional<String> formStrOpt = formStorage.getForm(formId);
        if (formStrOpt.isEmpty()) {
            form = new SupportForm();
            formId = formStorage.saveForm(form.toJson());
        } else {
            String formStr = formStrOpt.get();
            form = SupportForm.fromJson(formStr);
        }

        List<String> previousMessages = messageHistoryStorage.getMessages(formId);


        LLMResponse<SupportForm> response = llmService.generateFormContent(form, previousMessages, userInput, config);
        response.getUpdatedForm().isDataValid();
        response.getUpdatedForm().normalizeData();
        formStorage.updateForm(formId, response.getUpdatedForm().toJson());
        messageHistoryStorage.saveMessage(formId, userInput);
        response.setFormId(formId);

        return response;
    }

    /**
     * Retrieves the initial (empty) SupportForm.
     *
     * @return an empty SupportForm object
     */
    @Override
    public SupportForm getInitialForm() {
        return new SupportForm();
    }

}


