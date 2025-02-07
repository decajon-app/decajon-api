package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.ArtistDto;
import com.decajon.decajon.models.Artist;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ArtistMapper
{
    ArtistDto toDto(Artist artist);

    Artist toEntity(ArtistDto artistDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArtistFromDto(ArtistDto artistDto, @MappingTarget Artist artist);
}
