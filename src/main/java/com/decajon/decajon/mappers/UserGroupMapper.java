package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.models.UserGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserGroupMapper
{
    UserGroupDto toDto(UserGroup userGroup);
    UserGroup toEntity(UserGroupDto userGroupDto);
}
