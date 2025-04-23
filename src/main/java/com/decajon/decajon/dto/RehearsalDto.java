package com.decajon.decajon.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RehearsalDto
{
    private Long id;
    private Integer groupId;
    private String status;
    private LocalDateTime scheduledAt;
    private List<Long> songIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
