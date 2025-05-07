package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.models.Rehearsal;
import com.decajon.decajon.models.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RehearsalMapper
{
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "songIds", source = "songs", qualifiedByName = "toSongIds")
    RehearsalDto toDto(Rehearsal rehearsal);

    @Mapping(target = "songs", ignore = true)
    Rehearsal toEntity(RehearsalDto rehearsalDto);

    // Este metodo es auxiliar para extraer los ids de las canciones
    @Named("toSongIds")
    default List<Long> toSongIds(Set<Song> songs)
    {
        return songs.stream()
            .map(Song::getId)
            .collect(Collectors.toList());
    }
}
