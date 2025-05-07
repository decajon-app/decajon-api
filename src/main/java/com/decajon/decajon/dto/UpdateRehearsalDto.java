package com.decajon.decajon.dto;

import com.decajon.decajon.models.RehearsalStatus;

import java.time.LocalDateTime;

public record UpdateRehearsalDto
(
  RehearsalStatus status,
  LocalDateTime scheduledAt
)
{}
