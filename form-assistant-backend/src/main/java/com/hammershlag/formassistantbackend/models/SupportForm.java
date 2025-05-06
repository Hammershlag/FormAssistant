package com.hammershlag.formassistantbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.regex.Pattern;

/**
 * Represents a support form that holds user-submitted data such as name, email, contact reason, and urgency.
 * Provides methods for validating, normalizing, and serializing/deserializing the form.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportForm implements FormData{

    private String firstName;
    private String lastName;
    private String email;
    private String reasonOfContact;
    private Short urgency;

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_REASON_LENGTH = 100;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /**
     * Validates the current form data.
     *
     * @return true if all fields are valid
     * @throws InvalidDataException if any field contains invalid data
     */
    @JsonIgnore
    @Override
    public boolean isDataValid() {
        if (firstName == null || firstName.length() > MAX_NAME_LENGTH) {
            throw new InvalidDataException("First name is invalid: must be at most " + MAX_NAME_LENGTH + " characters.");
        }
        if (lastName == null || lastName.length() > MAX_NAME_LENGTH) {
            throw new InvalidDataException("Last name is invalid: must  be at most " + MAX_NAME_LENGTH + " characters.");
        }
        if (email == null || (!email.equals("null") && !email.equals("Unknown") && !EMAIL_PATTERN.matcher(email).matches())) {
            throw new InvalidDataException("Email is invalid: must  match the email pattern.");
        }
        if (reasonOfContact == null || reasonOfContact.length() > MAX_REASON_LENGTH) {
            throw new InvalidDataException("Reason of contact is invalid: must  be at most " + MAX_REASON_LENGTH + " characters.");
        }
        if (urgency == null || urgency < 0 || urgency > 10) {
            throw new InvalidDataException("Urgency is invalid: must be between 1 and 10.");
        }
        return true;
    }

    /**
     * Normalizes the form data by:
     * - Trimming whitespace from string fields
     * - Converting "null" or "unknown" (case-insensitive) to null
     * - Clamping urgency to a range of 1–10; if 0 or null, sets to null
     */
    public void normalizeData() {
        firstName = normalizeString(firstName);
        lastName = normalizeString(lastName);
        email = normalizeString(email);
        reasonOfContact = normalizeString(reasonOfContact);
        urgency = normalizeUrgency(urgency);
    }

    /**
     * Helper method for normalizing string values.
     *
     * @param value input string
     * @return trimmed and normalized string or null
     */
    private String normalizeString(String value) {
        if (value == null) return null;

        String trimmed = value.trim();
        return (trimmed.equalsIgnoreCase("null") || trimmed.equalsIgnoreCase("unknown")) ? null : trimmed;
    }

    /**
     * Helper method for normalizing urgency values.
     *
     * @param value input urgency
     * @return clamped urgency value or null if 0/null
     */
    private Short normalizeUrgency(Short value) {
        if (value == null || value == 0) return null;

        return (short) Math.max(1, Math.min(value, 10));
    }


    /**
     * Validates whether a given JSON string can be deserialized into a valid SupportForm object.
     *
     * @param json JSON string to validate
     * @return true if the structure is valid
     */
    public static boolean isStructureValid(String json) {
        try {
            new ObjectMapper().readValue(json, SupportForm.class);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * Serializes this SupportForm into a JSON string.
     *
     * @return JSON representation of the form
     * @throws RuntimeException if serialization fails
     */
    @Override
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize form", e);
        }
    }

    /**
     * Creates a SupportForm object from a JSON string.
     *
     * @param json the input JSON
     * @return SupportForm object
     * @throws InvalidDataException if parsing fails
     */
    @SneakyThrows
    public static SupportForm fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, SupportForm.class);
        } catch (JsonProcessingException e) {
            throw new InvalidDataException("Invalid JSON for SupportForm");
        }
    }
}
