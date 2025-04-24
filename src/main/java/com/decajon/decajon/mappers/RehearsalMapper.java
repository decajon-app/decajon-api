package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.models.Rehearsal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RehearsalMapper
{
    RehearsalDto toDto(Rehearsal rehearsal);

    @Mapping(target = "songs", ignore = true)
    Rehearsal toEntity(RehearsalDto rehearsalDto);
}
