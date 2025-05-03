package com.decajon.decajon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RepertoireSongCardDto
{
    private Long id; // id de la tabla repertoires
    private String song; // nombre de la cancion de la tabla songs
    private String artist; // nombre del arista de la tabla artists
}
