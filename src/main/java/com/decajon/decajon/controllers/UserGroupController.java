package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.UserGroupDto;
import com.decajon.decajon.services.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
