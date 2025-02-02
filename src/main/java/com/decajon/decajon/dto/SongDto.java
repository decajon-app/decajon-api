package com.decajon.decajon.dto;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
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
