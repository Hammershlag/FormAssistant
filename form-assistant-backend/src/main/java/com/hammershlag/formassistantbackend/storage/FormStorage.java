package com.hammershlag.formassistantbackend.storage;

import java.util.Optional;

/**
 * Interface for storing and managing form data.
 * This interface provides methods to save, update, retrieve, and delete form data.
 * The form data is represented as a JSON string.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
public interface FormStorage {

    /**
     * Saves a new form and returns a generated unique ID.
     * The form data is stored as a JSON string.
     *
     * @param json the JSON string representing the form to be saved
     * @return the generated unique ID for the saved form
     */
    String saveForm(String json);

    /**
     * Updates an existing form identified by the provided ID with new form data.
     * If the form with the given ID does not exist, an exception may be thrown.
     *
     * @param id the ID of the form to update
     * @param json the new JSON string to update the form with
     * @throws IllegalArgumentException if the form with the given ID does not exist
     */
    void updateForm(String id, String json);

    /**
     * Fetches the form by its unique ID.
     * If no form is found for the given ID, an empty Optional is returned.
     *
     * @param id the ID of the form to retrieve
     * @return an Optional containing the form data as a JSON string if found, or an empty Optional if not found
     */
    Optional<String> getForm(String id);

    /**
     * Deletes the form associated with the given ID.
     * If the form with the given ID does not exist, no action is taken.
     *
     * @param id the ID of the form to delete
     */
    void deleteForm(String id);
}

