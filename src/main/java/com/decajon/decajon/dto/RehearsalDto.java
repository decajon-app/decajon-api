package com.decajon.decajon.dto;

import com.decajon.decajon.models.RehearsalStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record RehearsalDto
(
    Long id,
    Long groupId,
    RehearsalStatus status,
    LocalDate scheduledAt,
    List<Long> songIds
) { }
