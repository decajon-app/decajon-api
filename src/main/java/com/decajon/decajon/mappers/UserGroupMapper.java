package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.models.UserGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserGroupMapper
{
    @Mapping(target = "id", expression = "java(new UserGroupId(userGroupDto.getUserId(), userGroupDto.getGroupId()))")
    UserGroup toEntity(UserGroupDto userGroupDto);

    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "groupId", source = "id.groupId")
    UserGroupDto toDto(UserGroup userGroup);
}
