package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.RepertoireDto;
import com.decajon.decajon.models.Repertoire;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RepertoireMapper
{
    RepertoireDto toDto(Repertoire repertoire);

    Repertoire toEntity(RepertoireDto repertoireDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRepertoireFromDto(RepertoireDto repertoireDto, @MappingTarget Repertoire repertoire);
}
