package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.SongDto;
import com.decajon.decajon.models.Song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SongMapper
{
    @Mapping(target = "formattedDuration",
             source = "duration",
             qualifiedByName = "formatDuration")
    SongDto toDto(Song song);

    @Mapping(target = "duration",
             source = "duration",
             qualifiedByName = "parseDuration")
    Song toEntity(SongDto songDto);

    @Named("formatDuration")
    static String formatDuration(int duration)
    {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Named("parseDuration")
    static int parseDuration(String formattedDuration)
    {
        String[] parts = formattedDuration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
