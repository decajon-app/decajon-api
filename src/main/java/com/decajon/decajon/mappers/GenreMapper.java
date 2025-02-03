package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.GenreDto;
import com.decajon.decajon.models.Genre;
import org.mapstruct.*;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenreMapper
{
    GenreDto toDto(Genre genre);

    Genre toEntity(GenreDto genreDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatedGenreFromDto(GenreDto genreDto, @MappingTarget Genre genre);
}
