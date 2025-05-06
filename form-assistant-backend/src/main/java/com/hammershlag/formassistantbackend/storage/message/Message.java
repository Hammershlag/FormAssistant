package com.hammershlag.formassistantbackend.storage.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 06.05.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    MessageSender sender;
    String message;

}
