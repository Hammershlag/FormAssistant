package com.hammershlag.formassistantbackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammershlag.formassistantbackend.dto.LLMResponse;
import com.hammershlag.formassistantbackend.models.FormData;
import com.hammershlag.formassistantbackend.services.config.LLMFormConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Tomasz Zbroszczyk
 * @version 1.0
 * @since 05.05.2025
 */
@Service
public class GeminiLLMService implements LLMService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public GeminiLLMService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T extends FormData> LLMResponse<T> generateFormContent(
            T form,
            String userInput,
            LLMFormConfig<T> config
    ) {
        String url = GEMINI_URL + apiKey;

        Map<String, Object> systemInstruction = Map.of(
                "parts", List.of(Map.of("text", config.getSystemInstruction()))
        );

        Map<String, Object> contents = Map.of(
                "parts", List.of(Map.of(
                        "text", "Here is the current form: " + form.toJson() + ". My input: '" + userInput + "'"
                ))
        );

        Map<String, Object> body = Map.of(
                "system_instruction", systemInstruction,
                "contents", List.of(contents),
                "generation_config", config.getGenerationConfig()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return parseLLMResponse(response.getBody(), config.getFormClass());
    }


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
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}
