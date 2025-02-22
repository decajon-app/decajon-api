package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.UserSongDto;
import com.decajon.decajon.models.UserSong;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSongMapper
{
    UserSongDto toDto(UserSong userSong);
    UserSong toEntity(UserSongDto userSongDto);
}
