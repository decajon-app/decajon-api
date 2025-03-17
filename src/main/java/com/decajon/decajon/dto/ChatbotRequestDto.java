package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChatbotRequestDto
{
    @NotBlank
    private String message;
}
