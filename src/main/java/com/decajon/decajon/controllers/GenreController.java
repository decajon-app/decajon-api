package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.GenreDto;
import com.decajon.decajon.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController
{
    private final GenreService genreService;


    /**
     *
     * @param genreDto
     * @return
     */
     @PostMapping
     public ResponseEntity<GenreDto> createGenre(@RequestBody @Valid GenreDto genreDto)
     {
         return ResponseEntity.ok(genreService.createGenre(genreDto));
     }


    /**
     * Metodo que regresa un genero por su id
     * @param id
     * @return ResponseEntity<GenreDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id)
    {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }


    /**
     * Este metodo recibe un groupId y retorna
     * todos los generos pertenecientes a ese groupId.
     * @param groupId
     * @return ResponseEntity<List<GenreDto>>
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GenreDto>> getGenresByGroupId(@PathVariable Long groupId)
    {
        return ResponseEntity.ok(genreService.getGenresByGroupId(groupId));
    }


    /**
     *
     * @param id
     * @param genreDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto)
    {
        return ResponseEntity.ok(genreService.updateGenre(id, genreDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id)
    {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
