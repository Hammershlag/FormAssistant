package com.hammershlag.formassistantbackend.storage.message;

import java.util.List;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 06.05.2025
 */
public interface MessageHistoryStorage {

    String saveMessage(String conversationId, MessageSender sender,String message);
    List<Message> getMessages(String conversationId);
    void deleteMessages(String conversationId);
    void deleteAllMessages();

}
