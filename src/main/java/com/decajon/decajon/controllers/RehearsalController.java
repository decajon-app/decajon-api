package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.dto.RehearsalListDto;
import com.decajon.decajon.dto.UpdateRehearsalDto;
import com.decajon.decajon.models.Rehearsal;
import com.decajon.decajon.services.RehearsalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rehearsals")
public class RehearsalController
{
    private final RehearsalService rehearsalService;

    /** Listar todos los ensayos de un grupo
     * GET - /api/groups/{groupId}/rehearsals
     * @param groupId
     * @return
     */
    @GetMapping("/groups/{groupId}/rehearsals")
    public ResponseEntity<RehearsalListDto> listByGroup(@PathVariable Long groupId)
    {
        List<RehearsalDto> rehearsals = rehearsalService.listByGroup(groupId);
        return ResponseEntity.ok(new RehearsalListDto(rehearsals));
    }

    /** Crear un nuevo ensayo (sin canciones o con lista inicial)
     * POST - /api/groups/{groupId}/rehearsals
     * @param groupId
     * @param rehearsalDto
     * @return
     */
    @PostMapping("/groups/{groupId}/rehearsals")
    public ResponseEntity<RehearsalDto> create(@PathVariable Long groupId, @Valid @RequestBody RehearsalDto rehearsalDto)
    {
        RehearsalDto newRehearsal = rehearsalService.create(groupId, rehearsalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRehearsal);
    }

    /** Obtener un ensayo por su ID
     * GET - /api/rehearsals/{rehearsalId}
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<RehearsalDto> getById(@PathVariable Long id)
    {
        RehearsalDto rehearsalDto = rehearsalService.getById(id);
        return ResponseEntity.ok(rehearsalDto);
    }


    /** Modificar campos de un ensayo
     *  PATCH - /api/rehearsals/{rehearsalId}
     * @param id
     * @return
     */
    @PatchMapping("/rehearsals/{id}")
    public ResponseEntity<RehearsalDto> updateById(@PathVariable Long id, @Valid @RequestBody UpdateRehearsalDto rehearsalDto)
    {
        RehearsalDto updatedRehearsal = rehearsalService.updateById(id, rehearsalDto);
        return ResponseEntity.ok(updatedRehearsal);
    }

    /** Eliminar un enseayo completo
     * DELETE - /api/rehearsals/{rehearsalId}
     * @param id
     * @return
     */
    @DeleteMapping("/rehearsals/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        rehearsalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /** Añadir una o varias canciones a un ensayo
     * POST - Añadir una o varias canciones a un ensayo
     * @param id
     * @return
     */
    @PostMapping("/{id}/songs")
    public ResponseEntity<RehearsalDto> addSongsById(@PathVariable Long id, @RequestBody Set<Long> songIds)
    {
        RehearsalDto rehearsalDto = rehearsalService.addSongsById(id, songIds);
        return ResponseEntity.ok(rehearsalDto);
    }

    /** Quitar una cancion concreta de un ensayo
     * DELETE - /api/rehearsals/{rehearsalId}/songs
     * @param id
     * @return
     */
    @DeleteMapping("/{id}/songs")
    public ResponseEntity<RehearsalDto> removeSongsById(@PathVariable Long id, @RequestBody Set<Long> songIds)
    {
        RehearsalDto rehearsalDto = rehearsalService.removeSongsById(id, songIds);
        return ResponseEntity.ok(rehearsalDto);
    }
}
