package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.CreateGroupDto;
import com.decajon.decajon.dto.GroupDto;
import com.decajon.decajon.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        if (groups.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204, si el usuario no tiene grupos
        }

        return ResponseEntity.ok(groups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id)
    {
        if(groupService.deleteGroup(id))
        {
          return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
