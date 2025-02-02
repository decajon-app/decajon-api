package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.SongDto;
import com.decajon.decajon.models.Song;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SongMapper
{
    @Mapping(target = "duration",
             source = "formattedDuration",
             qualifiedByName = "parseDuration")
    Song toEntity(SongDto songDto);


    @Mapping(target = "formattedDuration",
             source = "duration",
             qualifiedByName = "formatDuration")
    SongDto toDto(Song song);


    /**
     * Este metodo se usa para cuando se actualiza una cancion.
     * Se ignora el id porque no queremos sobreescribir ese campo.
     * El resto de columnas si se actualizan.
     * Recibimos un DTO del front y lo transformamos a una entidad
     * para guardarlo en la base de datos.
     * @param songDto
     * @param song
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "duration",
             source = "formattedDuration",
             qualifiedByName = "parseDuration",
             nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "title",
             target = "title",
             nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSongFromDto(SongDto songDto, @MappingTarget Song song);


    @Named("formatDuration")
    default String formatDuration(int duration)
    {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    @Named("parseDuration")
    default int parseDuration(String formattedDuration)
    {
        String[] parts = formattedDuration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
