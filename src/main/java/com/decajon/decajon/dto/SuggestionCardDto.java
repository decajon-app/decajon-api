package com.decajon.decajon.dto;

import java.time.Instant;
import java.time.OffsetDateTime;

public record SuggestionCardDto
(
        Long repertoireId,
        String title,
        String artist,
        String group,
        short performance,
        String dueDate
){}
