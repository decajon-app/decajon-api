package com.decajon.decajon.services;

import com.decajon.decajon.mappers.GroupMapper;
import com.decajon.decajon.models.Group;
import com.decajon.decajon.repositories.GroupRepository;
import com.decajon.decajon.dto.GroupDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService
{
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<GroupDto> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(groupMapper::toDto);
    }

    public GroupDto createGroup(GroupDto groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public boolean deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
