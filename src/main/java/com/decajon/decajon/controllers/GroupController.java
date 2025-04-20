package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.CreateGroupDto;
import com.decajon.decajon.dto.GroupDto;
import com.decajon.decajon.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        System.out.println("HOLA");
        GroupDto newGroup = groupService.createGroup(groupDto);
        return ResponseEntity.ok(newGroup);
    }


    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups()
    {
        return ResponseEntity.ok(groupService.getAllGroups());
    }


    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id)
    {
        Optional<GroupDto> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
