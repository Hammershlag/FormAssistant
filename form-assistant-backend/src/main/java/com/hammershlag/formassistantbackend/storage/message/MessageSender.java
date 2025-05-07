package com.hammershlag.formassistantbackend.storage.message;

import lombok.Getter;

/**
 * Enum representing the sender of a message in the system.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.1
 * @since 06.05.2025
 */
@Getter
public enum MessageSender {
    USER("user"),
    MODEL("model");

    /**
     * -- GETTER --
     *  Returns the sender identifier as a String.
     *
     * @return the sender name
     */
    private final String sender;

    MessageSender(String sender) {
        this.sender = sender;
    }

    /**
     * Converts a String to the appropriate MessageSender.
     *
     * @param sender the sender name
     * @return the corresponding MessageSender or null if not found
     */
    public static MessageSender fromString(String sender) {
        for (MessageSender ms : MessageSender.values()) {
            if (ms.sender.equalsIgnoreCase(sender)) {
                return ms;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return sender;
    }
}