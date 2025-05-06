package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.storage.form.InMemoryFormStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@SpringBootTest
class InMemoryFormStorageTests {

    private InMemoryFormStorage inMemoryFormStorage;

    @BeforeEach
    void setUp() {
        inMemoryFormStorage = new InMemoryFormStorage();
    }

    @Test
    void testSaveForm() {
        String json = "{\"name\":\"John Doe\", \"age\":30}";

        String id = inMemoryFormStorage.saveForm(json);

        assertNotNull(id);
        assertEquals(json, inMemoryFormStorage.getForm(id).orElse(null));
    }

    @Test
    void testUpdateForm() {
        String json = "{\"name\":\"Jane Doe\", \"age\":25}";
        String id = inMemoryFormStorage.saveForm(json);

        String updatedJson = "{\"name\":\"Jane Smith\", \"age\":26}";
        inMemoryFormStorage.updateForm(id, updatedJson);

        assertEquals(updatedJson, inMemoryFormStorage.getForm(id).orElse(null));
    }

    @Test
    void testUpdateNonExistentForm() {
        String invalidId = "invalid-id";
        String json = "{\"name\":\"John Doe\", \"age\":30}";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            inMemoryFormStorage.updateForm(invalidId, json);
        });

        assertEquals("Form with ID " + invalidId + " does not exist.", thrown.getMessage());
    }

    @Test
    void testGetForm() {
        String json = "{\"name\":\"Alice\", \"age\":28}";
        String id = inMemoryFormStorage.saveForm(json);

        Optional<String> retrievedForm = inMemoryFormStorage.getForm(id);

        assertTrue(retrievedForm.isPresent());
        assertEquals(json, retrievedForm.get());
    }

    @Test
    void testGetNonExistentForm() {
        String invalidId = "non-existent-id";

        Optional<String> retrievedForm = inMemoryFormStorage.getForm(invalidId);

        assertFalse(retrievedForm.isPresent());
    }

    @Test
    void testDeleteForm() {
        String json = "{\"name\":\"Bob\", \"age\":35}";
        String id = inMemoryFormStorage.saveForm(json);

        inMemoryFormStorage.deleteForm(id);

        assertFalse(inMemoryFormStorage.getForm(id).isPresent());
    }

    @Test
    void testDeleteNonExistentForm() {
        String invalidId = "invalid-id";

        inMemoryFormStorage.deleteForm(invalidId);

        assertTrue(true);
    }
}
