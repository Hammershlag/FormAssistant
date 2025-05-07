package com.hammershlag.formassistantbackend.exceptions;

import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.InvalidDataException;
import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.LLMException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@ControllerAdvice
public class ControllerExceptionHelper {


    /**
     * Use when user submits invalid data
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDetails> handleInvalidDataException(InvalidDataException e) {
        return ResponseEntity.status(400).body(new ExceptionDetails(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    /**
     * Use when server cannot process llm response
     * @param e
     * @return
     */
    @ExceptionHandler(LLMException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDetails> handleLLMException(LLMException e) {
        return ResponseEntity.status(500).body(new ExceptionDetails(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}