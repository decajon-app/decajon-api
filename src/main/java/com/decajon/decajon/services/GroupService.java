package com.decajon.decajon.services;

import com.decajon.decajon.dto.CreateGroupDto;
import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.mappers.GroupMapper;
import com.decajon.decajon.mappers.UserGroupMapper;
import com.decajon.decajon.models.Group;
import com.decajon.decajon.models.UserGroup;
import com.decajon.decajon.repositories.GroupRepository;
import com.decajon.decajon.dto.GroupDto;

import com.decajon.decajon.repositories.UserGroupRepository;
import com.decajon.decajon.utils.GroupPasswordGenerator;
import jakarta.transaction.Transactional;
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
    private final UserGroupMapper userGroupMapper;

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;


    @Transactional
    public GroupDto createGroup(CreateGroupDto groupRequestDto)
    {
        // Se verifica que el grupo no exista previamente
        // Nota: no pueden existir dos grupos con el mismo nombre
        if (groupRepository.findByName(groupRequestDto.getName()).isPresent())
        {
            throw new RuntimeException("El nombre del grupo ya existe.");
        }

        GroupDto newGroupDto = groupMapper.toDto(groupRequestDto);
        String generatedPassword = GroupPasswordGenerator.generateRandomPassword();
        newGroupDto.setPassword(generatedPassword);

        // Se guarda primero el grupo
        Group newGroup = groupMapper.toEntity(newGroupDto);
        Group savedGroup = groupRepository.save(newGroup);

        // Ahora se inserta la relacion usuario-grupo
        UserGroupDto newUserGroupDto = new UserGroupDto(
            savedGroup.getOwnerId(),
            savedGroup.getId(),
            savedGroup.getPassword(),
            "OWNER"
        );
        UserGroup newUserGroup = userGroupMapper.toEntity(newUserGroupDto);
        userGroupRepository.save(newUserGroup);

        // Se retornan los datos del grupo creado
        return groupMapper.toDto(savedGroup);
    }


    public Optional<GroupDto> getGroupById(Long id)
    {
        return groupRepository.findById(id)
                .map(groupMapper::toDto);
    }


    public List<GroupDto> getAllGroups()
    {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteGroup(Long id)
    {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
