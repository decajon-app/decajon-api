package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.mappers.UserGroupMapper;
import com.decajon.decajon.models.UserGroup;
import com.decajon.decajon.repositories.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService
{
    private final UserGroupRepository userGroupRepository;
    private final UserGroupMapper userGroupMapper;

    public List<UserGroupDto> getByUserId(Long userId)
    {
        return userGroupRepository.findByIdUserId(userId)
            .stream()
            .map(userGroupMapper::toDto)
            .collect(Collectors.toList()
        );
    }

    public List<UserGroupDto> getByGroupId(Long groupId)
    {
        return userGroupRepository.findByIdGroupId(groupId)
            .stream()
            .map(userGroupMapper::toDto)
            .collect(Collectors.toList()
        );
    }

    public UserGroupDto createUserGroup(UserGroupDto userGroupDto)
    {
        UserGroup newUserGroup = userGroupMapper.toEntity(userGroupDto);
        return userGroupMapper.toDto(
            userGroupRepository.save(newUserGroup)
        );
    }
}
