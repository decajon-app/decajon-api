package com.decajon.decajon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenreDto
{
    private Long id;
    private Long groupId;
    private String genre;
}
