package com.hammershlag.formassistantbackend;

import com.hammershlag.formassistantbackend.storage.message.InMemoryMessageStorage;
import com.hammershlag.formassistantbackend.storage.message.Message;
import com.hammershlag.formassistantbackend.storage.message.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 07.05.2025
 */
@SpringBootTest
public class InMemoryMessageStorageTests {

    private InMemoryMessageStorage messageStorage;

    @BeforeEach
    void setUp() {
        messageStorage = new InMemoryMessageStorage();
    }

    @Test
    void saveMessage_shouldStoreMessageForConversationId() {
        String conversationId = "conversation-1";
        String messageText = "Hello world";

        messageStorage.saveMessage(conversationId, MessageSender.USER, messageText);
        List<Message> messages = messageStorage.getMessages(conversationId);

        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getMessage()).isEqualTo(messageText);
        assertThat(messages.get(0).getSender()).isEqualTo(MessageSender.USER);
    }

    @Test
    void getMessages_shouldReturnEmptyListIfNoMessagesExist() {
        List<Message> messages = messageStorage.getMessages("unknown-id");

        assertThat(messages).isEmpty();
    }

    @Test
    void deleteMessages_shouldRemoveMessagesForConversationId() {
        String conversationId = "conversation-2";

        messageStorage.saveMessage(conversationId, MessageSender.USER, "Hi");
        messageStorage.deleteMessages(conversationId);

        List<Message> messages = messageStorage.getMessages(conversationId);
        assertThat(messages).isEmpty();
    }

    @Test
    void deleteAllMessages_shouldClearAllConversations() {
        messageStorage.saveMessage("id1", MessageSender.USER, "Msg 1");
        messageStorage.saveMessage("id2", MessageSender.MODEL, "Msg 2");

        messageStorage.deleteAllMessages();

        assertThat(messageStorage.getMessages("id1")).isEmpty();
        assertThat(messageStorage.getMessages("id2")).isEmpty();
    }

}
