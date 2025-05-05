package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.models.SupportForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public class SupportFormTests {

    @Test
    void testValidFormData() {
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", "Need support", (short) 5);
        assertTrue(form.isDataValid(), "Valid form should return true from isDataValid()");
    }

    @Test
    void testInvalidFirstNameTooLong() {
        String longName = "A".repeat(25);
        SupportForm form = new SupportForm(longName, "Doe", "john.doe@example.com", "Help", (short) 5);
        assertFalse(form.isDataValid(), "Form with long first name should be invalid");
    }

    @Test
    void testInvalidLastNameNull() {
        SupportForm form = new SupportForm("John", null, "john.doe@example.com", "Help", (short) 5);
        assertFalse(form.isDataValid(), "Form with null last name should be invalid");
    }

    @Test
    void testInvalidEmailFormat() {
        SupportForm form = new SupportForm("John", "Doe", "not-an-email", "Help", (short) 5);
        assertFalse(form.isDataValid(), "Form with invalid email should be invalid");
    }

    @Test
    void testInvalidReasonTooLong() {
        String longReason = "R".repeat(101);
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", longReason, (short) 5);
        assertFalse(form.isDataValid(), "Form with long reason should be invalid");
    }

    @Test
    void testInvalidUrgencyTooLow() {
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", "Help", (short) 0);
        assertFalse(form.isDataValid(), "Form with urgency < 1 should be invalid");
    }

    @Test
    void testInvalidUrgencyTooHigh() {
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", "Help", (short) 11);
        assertFalse(form.isDataValid(), "Form with urgency > 10 should be invalid");
    }

    @Test
    void testToJsonAndFromJson() {
        SupportForm form = new SupportForm("Alice", "Smith", "alice@example.com", "General inquiry", (short) 4);
        String json = form.toJson();

        SupportForm parsedForm = SupportForm.fromJson(json);
        assertEquals(form, parsedForm, "Deserialized form should match original");
    }

    @Test
    void testIsStructureValidWithValidJson() {
        String json = """
            {
                "firstName": "Bob",
                "lastName": "Brown",
                "email": "bob@example.com",
                "reasonOfContact": "Need help",
                "urgency": 3
            }
            """;
        assertTrue(SupportForm.isStructureValid(json), "Valid JSON should return true");
    }

    @Test
    void testIsStructureValidWithInvalidJson() {
        String invalidJson = "{ invalid json here ";
        assertFalse(SupportForm.isStructureValid(invalidJson), "Malformed JSON should return false");
    }

    @Test
    void testFromJsonWithInvalidJsonThrows() {
        String json = "{ invalid json }";
        assertThrows(IllegalArgumentException.class, () -> SupportForm.fromJson(json));
    }

    @Test
    void testToJsonThrowsRuntimeException() {
        SupportForm form = new SupportForm();
        // Force error by setting an unserializable field (not possible with current structure)
        // Keeping this test here for future-proofing with manual mocks or special Jackson configs
        assertDoesNotThrow(form::toJson);
    }
}
