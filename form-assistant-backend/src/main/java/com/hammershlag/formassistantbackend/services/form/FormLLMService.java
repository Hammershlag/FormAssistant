package com.hammershlag.formassistantbackend.services.form;

import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.FormData;

/**
 * Interface for handling operations related to the specific classes implementing FormData interface methods.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface FormLLMService<T extends FormData> {

    /**
     * Updates the FormData based on the provided user input.
     *
     * @param formId the ID of the existing form
     * @param userInput the user input that will be used to update the form
     * @return the updated form and a message explaining what was updated
     */
    LLMResponse<T> updateForm(String formId, String userInput);

    /**
     * Retrieves the initial (empty) FormData.
     *
     * @return an empty FormData object
     */
    T getInitialForm();
}
