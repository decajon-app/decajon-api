package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.CreateGroupDto;
import com.decajon.decajon.dto.GroupDto;
import com.decajon.decajon.dto.GroupMemberDto;
import com.decajon.decajon.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController
{
    private final GroupService groupService;


    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody @Valid CreateGroupDto groupDto)
    {
        GroupDto newGroup = groupService.createGroup(groupDto);
        return ResponseEntity.ok(newGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id)
    {
        Optional<GroupDto> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<GroupDto>> getGroupsByUserId(@PathVariable Long userId)
    {
        List<GroupDto> groups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroup(@PathVariable Long id)
    {
        if(groupService.deleteGroupById(id))
        {
            return ResponseEntity.ok(Map.of("message", "Grupo eliminado exitosamente"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Grupo no encontrado"));
    }

    @GetMapping("/{groupId}/members/count")
    public ResponseEntity<Long> getGroupMemberByCountById(@PathVariable Long groupId)
    {
        return ResponseEntity.ok(groupService.getGroupMembersCount(groupId));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberDto>> getGroupMembers(@PathVariable Long groupId)
    {
        Optional<List<GroupMemberDto>> members = groupService.getGroupMemberList(groupId);
        return members.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
