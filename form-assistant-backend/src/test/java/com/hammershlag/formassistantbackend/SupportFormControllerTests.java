package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.controllers.SupportFormController;
import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.InvalidDataException;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.form.SupportFormLLMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@ExtendWith(MockitoExtension.class)
class SupportFormControllerTests {

    @Mock
    private SupportFormLLMService llmService;

    @InjectMocks
    private SupportFormController supportFormController;

    @BeforeEach
    void setUp() {
        // Setup done by @InjectMocks
    }

    @Test
    void testGetInitialForm() {
        SupportForm initialForm = new SupportForm(null, null, null, null, null);
        when(llmService.getInitialForm()).thenReturn(initialForm);

        ResponseEntity<SupportForm> response = supportFormController.getInitialForm();
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody().getFirstName());
        assertNull(response.getBody().getLastName());
        assertNull(response.getBody().getEmail());
        assertNull(response.getBody().getReasonOfContact());
        assertNull(response.getBody().getUrgency());
    }

    @Test
    void testUpdateFormWithFormId() throws Exception {
        String formId = "12345";
        String userInput = "My name is John Doe and I need help!";
        SupportForm updatedForm = new SupportForm("null", "null", "null", "null", (short) 0);
        LLMResponse<SupportForm> llmResponse = new LLMResponse<>("I could not identify any fields from your input.", updatedForm);

        when(llmService.updateForm(formId, userInput)).thenReturn(llmResponse);

        ResponseEntity<LLMResponse<SupportForm>> response = supportFormController.updateForm(formId, userInput);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("I could not identify any fields from your input.", response.getBody().getMessage());
        assertEquals("null", response.getBody().getUpdatedForm().getFirstName());
    }

    @Test
    void testUpdateFormWithoutFormId() throws Exception {
        String userInput = "My name is Jane Doe and I need assistance!";
        SupportForm updatedForm = new SupportForm("Jane", "Doe", "jane.doe@example.com", "Assistance", (short) 3);
        LLMResponse<SupportForm> llmResponse = new LLMResponse<>("First name updated", updatedForm);

        when(llmService.updateForm("", userInput)).thenReturn(llmResponse);

        ResponseEntity<LLMResponse<SupportForm>> response = supportFormController.updateForm(null, userInput);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Jane", response.getBody().getUpdatedForm().getFirstName());
        assertEquals("Doe", response.getBody().getUpdatedForm().getLastName());
    }

    @Test
    void testUpdateFormWithTooLongFirstName() {
        String formId = "12345";
        String userInput = "My name is " + "J".repeat(25);

        when(llmService.updateForm(formId, userInput))
                .thenThrow(new InvalidDataException("First name is invalid: must be at most 20 characters."));

        InvalidDataException ex = assertThrows(
                InvalidDataException.class,
                () -> supportFormController.updateForm(formId, userInput)
        );

        assertEquals("First name is invalid: must be at most 20 characters.", ex.getMessage());
    }

    @Test
    void testUpdateFormWithInvalidEmail() {
        String formId = "12345";
        String userInput = "My email is notAnEmail.com";

        when(llmService.updateForm(formId, userInput))
                .thenThrow(new InvalidDataException("Email is invalid: must match the email pattern."));

        InvalidDataException ex = assertThrows(
                InvalidDataException.class,
                () -> supportFormController.updateForm(formId, userInput)
        );

        assertEquals("Email is invalid: must match the email pattern.", ex.getMessage());
    }

    @Test
    void testUpdateFormWithTooLongReason() {
        String formId = "12345";
        String userInput = "The reason why i contact you is " + "R".repeat(111);

        when(llmService.updateForm(formId, userInput))
                .thenThrow(new InvalidDataException("Reason of contact is invalid: must be at most 100 characters."));

        InvalidDataException ex = assertThrows(
                InvalidDataException.class,
                () -> supportFormController.updateForm(formId, userInput)
        );

        assertEquals("Reason of contact is invalid: must be at most 100 characters.", ex.getMessage());
    }

    @Test
    void testUpdateFormWithInvalidUrgency() {
        String formId = "12345";
        String userInput = "My urgency is 15";

        when(llmService.updateForm(formId, userInput))
                .thenThrow(new InvalidDataException("Urgency is invalid: must be between 1 and 10."));

        InvalidDataException ex = assertThrows(
                InvalidDataException.class,
                () -> supportFormController.updateForm(formId, userInput)
        );

        assertEquals("Urgency is invalid: must be between 1 and 10.", ex.getMessage());
    }
}
