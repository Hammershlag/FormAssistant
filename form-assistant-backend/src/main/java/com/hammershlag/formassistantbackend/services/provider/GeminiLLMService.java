package com.hammershlag.formassistantbackend.services.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.exceptions.exceptionTypes.LLMException;
import com.hammershlag.formassistantbackend.models.FormData;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import com.hammershlag.formassistantbackend.storage.message.Message;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service for interacting with the Gemini API to generate form content based on user input and form data.
 * Uses the Gemini language model to process user input and return an updated form.
 *
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Service
public class GeminiLLMService implements LLMService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String GEMINI_URL;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiLLMService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Generates updated form content using Gemini API based on the given form, previous messages, and user input.
     *
     * @param form the form data to be processed
     * @param previousMessages list of previous conversation messages
     * @param userInput the current user input to be processed
     * @param config the configuration specific to the form
     * @param <T> the type of the form data
     * @return the updated form and a message explaining the update
     * @throws LLMException if the request or response handling fails
     */
    @SneakyThrows
    public <T extends FormData> LLMResponse<T> generateFormContent(
            T form,
            List<Message> previousMessages,
            String userInput,
            LLMFormConfig<T> config
    ) {
        String url = GEMINI_URL + apiKey;

        String configRequest = config.getFormattedRequest(previousMessages, userInput, form, objectMapper);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(configRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return parseLLMResponse(response.getBody(), config.getFormClass());
    }

    /**
     * Parses the response from Gemini API and converts it to an LLMResponse object.
     *
     * @param json the raw JSON response from the Gemini API
     * @param clazz the class type of the form data
     * @param <T> the type of the form data
     * @return the parsed LLMResponse containing the updated form and message
     * @throws LLMException if parsing the response fails
     */
    private <T extends FormData> LLMResponse<T> parseLLMResponse(String json, Class<T> clazz) {
        try {
            JsonNode root = objectMapper.readTree(json);
            String textBlock = root.path("candidates").get(0)
                    .path("content").path("parts").get(0).path("text").asText();

            JsonNode innerJson = objectMapper.readTree(textBlock);
            String message = innerJson.path("message").asText();
            JsonNode updatedFormNode = innerJson.path("updated_form");

            T updatedForm = objectMapper.treeToValue(updatedFormNode, clazz);

            return new LLMResponse<>(message, updatedForm);

        } catch (Exception e) {
            throw new LLMException("Failed to parse Gemini response");
        }
    }
}
