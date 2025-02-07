package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.RepertoireDto;
import com.decajon.decajon.services.RepertoireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/repertoires")
public class RepertoireController
{
    public final RepertoireService repertoireService;


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
    @GetMapping("/group/{id}")
    public ResponseEntity<List<RepertoireDto>> getRepertoireByGroupId(@PathVariable Long groupId)
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
