package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.SongDto;
import com.decajon.decajon.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController
{
    private final SongService songService;


    /**
     * Metodo que crea una nueva cancion y regresa la cancion creada y el status.
     * @param songDto
     * @return ResponseEntity<SongDto>
     */
    @PostMapping
    public ResponseEntity<SongDto> createSong(@RequestBody SongDto songDto)
    {
        SongDto createdSong = songService.createSong(songDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    /**
     * Metodo que regresa todas las canciones pertenecientes a un grupo
     * @param groupId
     * @return ResponseEntity<List<SongDto>>
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<SongDto>> getSongsByGroup(@PathVariable Long groupId)
    {
        List<SongDto> songs = songService.getSongsByGroupId(groupId);
        return ResponseEntity.ok(songs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id)
    {
        SongDto song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDto> updateSong(@PathVariable Long id, @RequestBody SongDto songDto)
    {
        SongDto updatedSong = songService.updateSong(id, songDto);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id)
    {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
