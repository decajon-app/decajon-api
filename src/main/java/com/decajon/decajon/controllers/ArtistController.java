package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.ArtistDto;
import com.decajon.decajon.services.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController
{
    private final ArtistService artistService;


    /**
     *
     * @param artistDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ArtistDto> createArtist(@RequestBody @Valid ArtistDto artistDto)
    {
        return ResponseEntity.ok(artistService.createArtist(artistDto));
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable Long id)
    {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/group/{id}")
    public ResponseEntity<List<ArtistDto>> getArtistsByGroupId(@PathVariable Long id)
    {
        return ResponseEntity.ok(artistService.getArtistsByGroupId(id));
    }


    /**
     *
     * @param id
     * @param artistDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtistById(@PathVariable Long id, @RequestBody ArtistDto artistDto)
    {
        return ResponseEntity.ok(artistService.updateArtist(id, artistDto));
    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable Long id)
    {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
