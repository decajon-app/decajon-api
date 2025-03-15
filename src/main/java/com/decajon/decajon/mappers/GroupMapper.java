package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.CreateGroupDto;
import com.decajon.decajon.dto.GroupDto;
import com.decajon.decajon.models.Group;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class GroupMapper
{
    public abstract GroupDto toDto(Group group);

    public abstract Group toEntity(CreateGroupDto createGroupDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateGroupFromDto(GroupDto groupDto, @MappingTarget Group group);
}
