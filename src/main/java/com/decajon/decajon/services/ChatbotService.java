package com.decajon.decajon.services;

import com.decajon.decajon.dto.ChatbotRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatbotService
{
    private final RestTemplate restTemplate;

    public ResponseEntity<?> sendMessage(ChatbotRequestDto request)
    {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ChatbotRequestDto> entity = new HttpEntity<>(request, headers);

            String CHATBOT_URL = "http://localhost:8000/api/chatbot";
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    CHATBOT_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e)
        {
            return ResponseEntity.status(500).body(Map.of("error", "Error de comunicacion con el chatbot", "details", e.getMessage()));
        }
    }
}
