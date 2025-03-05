package com.decajon.decajon.mappers;

import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.dto.UserRequestDto;
import com.decajon.decajon.models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserMapper
{
    public abstract UserDto toDto(User user);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    public abstract User toEntity(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
