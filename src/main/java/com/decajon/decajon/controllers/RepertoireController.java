package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.RepertoireDto;
import com.decajon.decajon.dto.RepertoireRequestDto;
import com.decajon.decajon.dto.RepertoireSongCardDto;
import com.decajon.decajon.services.RepertoireService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/repertoires")
public class RepertoireController
{
    public final RepertoireService repertoireService;


    /**
     * Recibe un DTO que contiene toda la información de la canción a agregar
     * Incluído:
     * - Artista
     * - Genero
     * - Cancion
     * Dentro del servicio se desmenuza este objeto para repartirlo en sus respectivas tablas.
     * @param repertoireDto
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addSongToRepertoire(@RequestBody @Valid RepertoireRequestDto repertoireDto)
    {
        try
        {
            repertoireService.addSong(repertoireDto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Canción agregada al repertorio correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al añadir la canción al repertorio");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>("Error al añador la canción al repertorio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param repertoireDto
     * @return
     */
    @PostMapping
    public ResponseEntity<RepertoireDto> createRepertoire(@RequestBody @Valid RepertoireDto repertoireDto)
    {
        return ResponseEntity.ok(repertoireService.createRepertoire(repertoireDto));
    }


    /**
     *
     * @param groupId
     * @return
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<RepertoireSongCardDto>> getRepertoireByGroupId(@PathVariable Long groupId)
    {
        return ResponseEntity.ok(repertoireService.getRepertoireByGroupId(groupId));
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepertoireDto> getRepertoireById(@PathVariable Long id)
    {
       return ResponseEntity.ok(repertoireService.getRepertoireById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RepertoireDto> updateRepertoireById(@PathVariable Long id, @RequestBody RepertoireDto repertoireDto)
    {
        return ResponseEntity.ok(repertoireService.updateRepertoire(id, repertoireDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepertoireById(@PathVariable Long id)
    {
        repertoireService.deleteRepertoire(id);
        return ResponseEntity.noContent().build();
    }
}
