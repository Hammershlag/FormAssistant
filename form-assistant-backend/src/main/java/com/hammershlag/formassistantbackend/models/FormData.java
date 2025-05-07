package com.hammershlag.formassistantbackend.models;

import java.io.Serializable;

/**
 * Interface representing a form data structure.
 * This interface provides methods to validate the data and convert it to a JSON string.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface FormData extends Serializable {

    /**
     * Checks whether the data inside this form instance is valid (e.g. email format, field lengths).
     */
    boolean isDataValid();

    /**
     * Checks whether the structure of the form is valid (e.g. all required fields are present).
     * This method should be overridden in the implementing class.
     *
     * @param formData the form data to check
     * @return true if the structure is valid, false otherwise
     */
    static boolean isStructureValid(FormData formData) {
        throw new UnsupportedOperationException("Override this method in the implementing class.");
    }

    /**
     * Converts the form data to a JSON string.
     * This method should be overridden in the implementing class.
     *
     * @return the JSON string representation of the form data
     */
    String toJson();
}
