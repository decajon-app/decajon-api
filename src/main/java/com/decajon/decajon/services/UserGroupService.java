package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.mappers.UserGroupMapper;
import com.decajon.decajon.models.Group;
import com.decajon.decajon.models.UserGroup;
import com.decajon.decajon.models.UserGroupId;
import com.decajon.decajon.repositories.GroupRepository;
import com.decajon.decajon.repositories.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService
{
    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;
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

    /**
     * En esta funcion hacemos dos cosas:
     * 1. Validamos que el grupo existe y que la contraseña sea correcta.
     * 2. Creamos la relación Usuario-Grupo si el punto anterior es exitoso.
     * @param userGroupDto
     * @return
     */
    public UserGroupDto createUserGroup(UserGroupDto userGroupDto)
    {
        // Buscar el grupo por su id
        Optional<Group> optGroup = groupRepository.findById(userGroupDto.getGroupId());
        if(optGroup.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El grupo no existe.");
        }

        // Recuperar el modelo del grupo (Ya no el Optional)
        Group group = optGroup.get();

        // Valida contaseña (Negacion porque queremos lo contrario a equals)
        if(!group.getPassword().equals(userGroupDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La contraseña del grupo es incorrecta.");
        }

        // Una vez que el grupo existe y la contraseña es correcta, guardar la relacion usergroup
        UserGroupDto newUserGroupDto = new UserGroupDto(
                userGroupDto.getUserId(),
                userGroupDto.getGroupId(),
                userGroupDto.getPassword(),
                "MEMBER"
        );

        UserGroup newUserGroup = userGroupMapper.toEntity(newUserGroupDto);
        UserGroup createdUserGroup = userGroupRepository.save(newUserGroup);
        return userGroupMapper.toDto(createdUserGroup);
    }
}
