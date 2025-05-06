package com.hammershlag.formassistantbackend.storage.message;

/**
 * Enum representing the sender of a message in the system.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.1
 * @since 06.05.2025
 */
public enum MessageSender {
    USER("user"),
    MODEL("model");

    private final String sender;

    MessageSender(String sender) {
        this.sender = sender;
    }

    /**
     * Returns the sender identifier as a String.
     *
     * @return the sender name
     */
    public String getSender() {
        return sender;
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