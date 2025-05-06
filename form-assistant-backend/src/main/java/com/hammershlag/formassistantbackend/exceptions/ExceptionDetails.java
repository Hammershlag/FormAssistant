package com.hammershlag.formassistantbackend.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */

@Getter
public class ExceptionDetails {

    private final HttpStatus status;

    private final String errorMessage;

    public ExceptionDetails(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
