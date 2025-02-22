package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.UserSongDto;
import com.decajon.decajon.services.UserSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users-songs")
public class UserSongController
{
    private final UserSongService  userSongService;

    @PostMapping
    public ResponseEntity<UserSongDto> save(@RequestBody UserSongDto userSongDto) {
        return ResponseEntity.ok(userSongService.save(userSongDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSongDto> findById(@PathVariable Long id) {
        return userSongService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user-group-song")
    public ResponseEntity<UserSongDto> findByUserGroupSong(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Long songId) {
        return userSongService.findByUserGroupSong(userId, groupId, songId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-group-song")
    public ResponseEntity<List<UserSongDto>> findByGroupAndSong(@RequestParam Long groupId, @RequestParam Long songId) {
        return ResponseEntity.ok(userSongService.findByGroupAndSong(groupId, songId));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<UserSongDto>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userSongService.findByUser(userId));
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<UserSongDto>> findByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(userSongService.findByGroup(groupId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Long songId) {
        userSongService.delete(userId, groupId, songId);
        return ResponseEntity.noContent().build();
    }
}
