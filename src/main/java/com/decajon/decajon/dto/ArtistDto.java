package com.decajon.decajon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDto
{
    private Long id;
    private Long groupId;
    private String artist;
}
