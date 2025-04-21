package com.decajon.decajon.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RepertoireRequestDto
{
    @NotNull
    private Long groupId; // ID del grupo al que se le va a agregar

    @NotBlank
    private String title; // Nombre de la cancion

    private String artist;

    private String genre;

    private String comment;

    private String tone;

    @Min(0)
    @Max(10)
    private int performance;

    @Min(0)
    @Max(10)
    private Long popularity;

    @Min(0)
    @Max(10)
    private int complexity;

    private int duration;
}
