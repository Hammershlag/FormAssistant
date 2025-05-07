package com.hammershlag.formassistantbackend.services.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.models.SupportForm;
import com.hammershlag.formassistantbackend.storage.message.Message;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Configuration for the SupportForm used with the large language model (LLM).
 * Defines system instructions and generation configuration for form processing.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Component
public class SupportFormLLMConfig implements LLMFormConfig<SupportForm> {

    private static final String configFilePath  = "support_form_request.fmt";

    /**
     * Returns the class type of the support form.
     * Ensures the correct form type is used in the process.
     *
     * @return the SupportForm class type
     */
    @Override
    public Class<SupportForm> getFormClass() {
        return SupportForm.class;
    }

    /**
     * Generates a formatted request string for the large language model (LLM) based on the provided context.
     * This includes previous messages, user input, and the current state of the form.
     *
     * The method reads a predefined request format from a configuration file, serializes the context into JSON,
     * and injects it into the format string. This ensures the LLM receives a structured and consistent input.
     *
     * @param previousMessages a list of previous messages exchanged in the conversation
     * @param userInput the user's current input to be processed
     * @param form the current state of the form being processed
     * @param objectMapper an instance of ObjectMapper for JSON serialization
     * @return a formatted request string ready to be sent to the LLM
     * @throws IOException if there is an error reading the configuration file or serializing the data
     */
    @Override
    public String getFormattedRequest(List<Message> previousMessages, String userInput, SupportForm form, ObjectMapper objectMapper) throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        List<Map<String, Object>> contents = previousMessages.stream()
                .map(msg -> Map.of(
                        "role", msg.getSender(),
                        "parts", List.of(Map.of("text", "Message " + counter.getAndIncrement() + ": " + msg.getMessage()))
                )).collect(Collectors.toList());

        String currentMessage = "Here is the current form: " + form.toJson() +
                ". My input: '" + userInput + "'";

        contents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", currentMessage))
        ));

        String requestFmt = new ClassPathResource(configFilePath).getContentAsString(StandardCharsets.UTF_8);
        String contentsString = objectMapper.writeValueAsString(contents);
        return requestFmt.formatted(contentsString);
    }
}
