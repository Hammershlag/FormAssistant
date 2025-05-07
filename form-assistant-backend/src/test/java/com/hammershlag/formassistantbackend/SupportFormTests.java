package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.InvalidDataException;
import com.hammershlag.formassistantbackend.models.SupportForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@SpringBootTest
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

        // Expecting the InvalidDataException to be thrown due to long first name
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);

        // Optionally, check the exception message to ensure it is as expected
        assertEquals("First name is invalid: must be at most 20 characters.", exception.getMessage());
    }


    @Test
    void testInvalidLastNameNull() {
        SupportForm form = new SupportForm("John", null, "john.doe@example.com", "Help", (short) 5);

        // Expecting InvalidDataException to be thrown for null last name
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);
        assertEquals("Last name is invalid: must  be at most 20 characters.", exception.getMessage());
    }

    @Test
    void testInvalidEmailFormat() {
        SupportForm form = new SupportForm("John", "Doe", "not-an-email", "Help", (short) 5);

        // Expecting InvalidDataException to be thrown for invalid email format
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);
        assertEquals("Email is invalid: must  match the email pattern.", exception.getMessage());
    }

    @Test
    void testInvalidReasonTooLong() {
        String longReason = "R".repeat(101);
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", longReason, (short) 5);

        // Expecting InvalidDataException to be thrown for reason that is too long
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);
        assertEquals("Reason of contact is invalid: must  be at most 100 characters.", exception.getMessage());
    }

    @Test
    void testInvalidUrgencyTooLow() {
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", "Help", (short) -1);

        // Expecting InvalidDataException to be thrown for urgency less than 1
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);
        assertEquals("Urgency is invalid: must be between 1 and 10.", exception.getMessage());
    }

    @Test
    void testInvalidUrgencyTooHigh() {
        SupportForm form = new SupportForm("John", "Doe", "john.doe@example.com", "Help", (short) 11);

        // Expecting InvalidDataException to be thrown for urgency greater than 10
        InvalidDataException exception = assertThrows(InvalidDataException.class, form::isDataValid);
        assertEquals("Urgency is invalid: must be between 1 and 10.", exception.getMessage());
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
