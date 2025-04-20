package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.ChatbotRequestDto;
import com.decajon.decajon.services.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController
{
    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<?> sendMessageToChatbot(@RequestBody @Valid ChatbotRequestDto request)
    {
        return chatbotService.sendMessage(request);
    }
}
