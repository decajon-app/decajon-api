package com.decajon.decajon.dto;

import com.decajon.decajon.models.CardData;

import java.time.OffsetDateTime;

public record ReviewCardRequestDto
(
        int rating,
        OffsetDateTime review_datetime,
        CardData card_data
)
{}
