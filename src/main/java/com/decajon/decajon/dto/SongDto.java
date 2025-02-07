package com.decajon.decajon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SongDto
{
    private Long id;
    private Long groupId;
    private Long artistId;
    private Long genreId;
    private String title;
    private Integer duration;
    private String formattedDuration;
}
