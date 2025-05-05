package com.hammershlag.formassistantbackend.storage;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 * In-memory storage implementation for storing form data.
 */

@Component
public class InMemoryStorage implements FormStorage {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    /**
     * Saves a form JSON string and returns a unique ID.
     *
     * @param json the JSON string representing the form
     * @return the generated ID for the saved form
     */
    @Override
    public String saveForm(String json) {
        String id = UUID.randomUUID().toString();
        store.put(id, json);
        return id;
    }

    /**
     * Updates an existing form with a new JSON string.
     *
     * @param id the ID of the form to update
     * @param json the new form data in JSON format
     * @throws IllegalArgumentException if the form with the given ID does not exist
     */
    @Override
    public void updateForm(String id, String json) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("Form with ID " + id + " does not exist.");
        }
        store.put(id, json);
    }

    /**
     * Retrieves the form data by its ID.
     *
     * @param id the ID of the form to retrieve
     * @return an Optional containing the form data, or an empty Optional if not found
     */
    @Override
    public Optional<String> getForm(String id) {
        String formData = store.get(id);
        if (formData == null || formData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(store.get(id));
    }

    /**
     * Deletes a form by its ID.
     *
     * @param id the ID of the form to delete
     */
    @Override
    public void deleteForm(String id) {
        store.remove(id);
    }
}
