package com.decajon.decajon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class GenreDto
{
    private Long id;
    private Long groupId;
    private String genre;
}
