package com.decajon.decajon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RepertoireSongDto
{
    private Long repertoireId;
    private String tone;
    private String comment;
    private Integer performance;
    private Long popularity;
    private Integer complexity;
    private LocalDateTime practicedAt;
    private String title;
    private Integer duration;
    private String genre;
    private String artist;

    public RepertoireSongDto(
            Long repertoireId,
            String tone,
            String comment,
            Integer performance,
            Long popularity,
            Integer complexity,
            LocalDateTime practicedAt,
            String title,
            Integer duration,
            String genre,
            String artist
    ) {
        this.repertoireId = repertoireId;
        this.tone = tone;
        this.comment = comment;
        this.performance = performance;
        this.popularity = popularity;
        this.complexity = complexity;
        this.practicedAt = practicedAt;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.artist = artist;
    }
}