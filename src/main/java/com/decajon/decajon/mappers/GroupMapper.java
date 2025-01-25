package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.GroupDto;
import com.decajon.decajon.models.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper
{
    GroupDto toDto(Group group);
    Group toEntity(GroupDto groupDto);
}
