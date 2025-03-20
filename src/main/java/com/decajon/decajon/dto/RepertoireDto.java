package com.decajon.decajon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepertoireDto
{
    private Long id;
    private Long songId;
    private String songTitle;
    private String tone;
    private String comment;
    private int performance;
    private int popularity;
    private LocalDateTime practiced;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
