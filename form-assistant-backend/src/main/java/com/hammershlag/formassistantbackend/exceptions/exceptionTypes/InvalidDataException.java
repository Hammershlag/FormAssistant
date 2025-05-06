package com.hammershlag.formassistantbackend.exceptions.exceptionTypes;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
