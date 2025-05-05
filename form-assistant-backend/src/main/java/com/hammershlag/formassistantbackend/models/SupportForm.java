package com.hammershlag.formassistantbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
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

    public static boolean isStructureValid(String json) {
        try {
            new ObjectMapper().readValue(json, SupportForm.class);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @Override
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize form", e);
        }
    }

    public static SupportForm fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, SupportForm.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON for SupportForm", e);
        }
    }
}
