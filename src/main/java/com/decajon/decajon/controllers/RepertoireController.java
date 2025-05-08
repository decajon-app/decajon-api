package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.*;
import com.decajon.decajon.services.RepertoireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{repertoireId}/song-details")
    public ResponseEntity<?> getSongDetails(@PathVariable Long repertoireId)
    {
        Optional<RepertoireSongDto> songDetails = repertoireService.getSongDetailsByRepertoireId(repertoireId);
        if (songDetails.isPresent())
        {
            return new ResponseEntity<>(songDetails.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     */
    @PostMapping("/review-card")
    public ResponseEntity<Map<String, String>> reviewCard(@RequestBody @Valid RepertoireReviewSongDto reviewSongDto)
    {
        try
        {
            repertoireService.reviewCard(reviewSongDto);
            return ResponseEntity.ok(Map.of("message", "Se ha hecho review de la card correctamente"));
        }
        catch (Exception e)
        {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("Error", "Error procesando la revisión de la carta: " + e.getMessage()));
        }
    }

    /**
     * Este endpoint recibe el id del usuario y regresa la lista de canciones que tiene para ensayar.
     * En el service busca todos los grupos a los que pertenece ese usuario, y despues busca todas
     * las sugerencias que tiene para ensayar.
     *
     * Regresa una lista de sugerencias, cada sugerencia tiene los siguientes campos:
     * - ID del repertorio
     * - Nombre de la cancion
     * - Grupo al que pertenece la sugerencia
     * - Artista al que pertenece la cancion
     * - Fecha en que vence el ensayo
     * - Rendimiento de la cancion
     * @param userId
     * @return List<SuggestionCardDto>
     */
    @GetMapping("/user/{userId}/suggestions")
    public ResponseEntity<List<SuggestionCardDto>> getSuggestionsByUserId(@PathVariable Long userId)
    {
        List<SuggestionCardDto> suggestions = repertoireService.getSuggestionsByUserId(userId);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/group/{groupId}/suggestions")
    public ResponseEntity<List<SuggestionCardDto>> getSuggestionsByGroupId(@PathVariable Long groupId)
    {
        List<SuggestionCardDto> suggestions = repertoireService.getSuggestionsByGroupId(groupId);
        return ResponseEntity.ok(suggestions);
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
