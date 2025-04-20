package com.decajon.decajon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class UserSongDto
{
    private Long id;
    private Long userId;
    private Long groupId;
    private Long songId;
    private int performance;
    private LocalDateTime practicedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
