package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.controllers.SupportFormController;
import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.services.form.SupportFormLLMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@SpringBootTest
public class SupportFormControllerTests {
    private SupportFormController controller;
    private SupportFormLLMService llmService;

    @BeforeEach
    void setUp() {
        llmService = mock(SupportFormLLMService.class);
        controller = new SupportFormController(llmService);
    }

    @Test
    void testGetInitialForm() {
        SupportForm mockForm = new SupportForm();
        when(llmService.getInitialForm()).thenReturn(mockForm);

        ResponseEntity<SupportForm> response = controller.getInitialForm();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockForm, response.getBody());
        verify(llmService, times(1)).getInitialForm();
    }

    @Test
    void testUpdateForm_WithValidInput() throws Exception {
        String userInput = "Set first name to John and last name to Doe";
        SupportForm updatedForm = new SupportForm("John", "Doe", null, null, null);
        LLMResponse<SupportForm> mockResponse = new LLMResponse<>("Form updated successfully", updatedForm, "12345");

        when(llmService.updateForm("", userInput)).thenReturn(mockResponse);

        ResponseEntity<LLMResponse<SupportForm>> response = controller.updateForm(null, userInput);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedForm, response.getBody().getUpdatedForm());
        verify(llmService, times(1)).updateForm("", userInput);
    }

    @Test
    void testUpdateForm_WithFormId() throws Exception {
        String formId = "12345";
        String userInput = "Set urgency to 5";
        SupportForm updatedForm = new SupportForm("John", "Doe", "john.doe@example.com", "Need help", (short) 5);
        LLMResponse<SupportForm> mockResponse = new LLMResponse<>("Urgency updated successfully", updatedForm, formId);

        when(llmService.updateForm(formId, userInput)).thenReturn(mockResponse);

        ResponseEntity<LLMResponse<SupportForm>> response = controller.updateForm(formId, userInput);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedForm, response.getBody().getUpdatedForm());
        verify(llmService, times(1)).updateForm(formId, userInput);
    }

    @Test
    void testUpdateForm_InvalidInput() throws Exception {
        String userInput = "Set urgency to 15"; // Invalid urgency

        when(llmService.updateForm("", userInput)).thenThrow(new RuntimeException("Urgency is invalid: must be between 1 and 10."));

        Exception exception = assertThrows(RuntimeException.class, () -> controller.updateForm(null, userInput));
        assertEquals("Urgency is invalid: must be between 1 and 10.", exception.getMessage());

        verify(llmService, times(1)).updateForm("", userInput);
    }

    @Test
    void testUpdateForm_TwoMessages_UpdateTwoFields() throws Exception {
        String firstMessage = "Set first name to John";
        String secondMessage = "Set last name to Doe";

        SupportForm intermediateForm = new SupportForm("John", null, null, null, null);
        SupportForm finalForm = new SupportForm("John", "Doe", null, null, null);

        LLMResponse<SupportForm> firstResponse = new LLMResponse<>("First name updated", intermediateForm, "12345");
        LLMResponse<SupportForm> secondResponse = new LLMResponse<>("Last name updated", finalForm, "12345");

        when(llmService.updateForm("", firstMessage)).thenReturn(firstResponse);
        when(llmService.updateForm("12345", secondMessage)).thenReturn(secondResponse);

        // First update
        ResponseEntity<LLMResponse<SupportForm>> firstResponseEntity = controller.updateForm(null, firstMessage);
        assertNotNull(firstResponseEntity);
        assertEquals(200, firstResponseEntity.getStatusCodeValue());
        assertEquals(intermediateForm, firstResponseEntity.getBody().getUpdatedForm());

        // Second update
        ResponseEntity<LLMResponse<SupportForm>> secondResponseEntity = controller.updateForm("12345", secondMessage);
        assertNotNull(secondResponseEntity);
        assertEquals(200, secondResponseEntity.getStatusCodeValue());
        assertEquals(finalForm, secondResponseEntity.getBody().getUpdatedForm());

        verify(llmService, times(1)).updateForm("", firstMessage);
        verify(llmService, times(1)).updateForm("12345", secondMessage);
    }
}
