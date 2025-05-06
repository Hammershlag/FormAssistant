package com.hammershlag.formassistantbackend.storage.message;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 06.05.2025
 */

@Component
public class InMemoryMessageStorage implements MessageHistoryStorage {

    private final Map<String, List<Message>> messageStore = new ConcurrentHashMap<>();

    /**
     * Deletes all messages from the storage.
     */
    @Override
    public void deleteAllMessages() {
        messageStore.clear();
    }

    /**
     * Saves a message in the storage.
     *
     * @param conversationId the ID of the conversation
     * @param message the message to save
     * @return the conversation ID
     */
    @Override
    public String saveMessage(String conversationId, MessageSender sender, String message) {
        messageStore.computeIfAbsent(conversationId, k -> new java.util.ArrayList<>()).add(new Message(sender, message));
        return conversationId;
    }

    /**
     * Retrieves messages for a given conversation ID.
     *
     * @param conversationId the ID of the conversation
     * @return a list of messages for the conversation
     */
    @Override
    public List<Message> getMessages(String conversationId) {
        return new ArrayList<>(messageStore.getOrDefault(conversationId, List.of()));
    }

    /**
     * Deletes messages for a given conversation ID.
     *
     * @param conversationId the ID of the conversation
     */
    @Override
    public void deleteMessages(String conversationId) {
        messageStore.remove(conversationId);
    }
}
