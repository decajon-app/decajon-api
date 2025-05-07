package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.services.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users-groups")
public class UserGroupController
{
    private final UserGroupService userGroupService;

    @GetMapping("/user/{userId}")
    public List<UserGroupDto> getByUser(@PathVariable Long userId)
    {
        return userGroupService.getByUserId(userId);
    }

    @GetMapping("/group/{groupId}")
    public List<UserGroupDto> getByGroup(@PathVariable Long groupId)
    {
        return userGroupService.getByGroupId(groupId);
    }

    @PostMapping
    public UserGroupDto addUserToGroup(@RequestBody UserGroupDto newUserGroupDto)
    {
        return userGroupService.createUserGroup(newUserGroupDto);
    }

    @GetMapping("/user/{userId}/group/{groupId}/permissions")
    public Map<String, String> getRoleByUserIdAndGroupId(@PathVariable Long userId, @PathVariable Long groupId)
    {
        String role = userGroupService.getPermissionLevel(userId, groupId);
        return Map.of("role", role);
    }

    @DeleteMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<Map<String, String>> removeMember(@PathVariable Long groupId, @PathVariable Long userId)
    {
        try
        {
            boolean isDeleted = userGroupService.removeMemberFromGroup(groupId, userId);
            if(isDeleted)
            {

                return ResponseEntity.ok(Map.of("message", "Miembro eliminado correctamente"));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Miembro no encontrado"));
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", "Error al eliminar al miembro"));
        }
    }
}
