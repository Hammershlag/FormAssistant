package com.hammershlag.formassistantbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return firstName != null && firstName.length() <= MAX_NAME_LENGTH &&
                lastName != null && lastName.length() <= MAX_NAME_LENGTH &&
                email != null && EMAIL_PATTERN.matcher(email).matches() &&
                reasonOfContact != null && reasonOfContact.length() <= MAX_REASON_LENGTH &&
                urgency >= 1 && urgency <= 10;
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
