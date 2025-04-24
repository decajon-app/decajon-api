package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.RehearsalDto;
import com.decajon.decajon.services.RehearsalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rehearsals")
public class RehearsalController
{
    private final RehearsalService rehearsalService;

    @GetMapping("/{id}")
    public ResponseEntity<RehearsalDto> getRehearsalById(@PathVariable Long id)
    {
        return ResponseEntity.ok(rehearsalService.getRehearsalById(id));
    }

    @PostMapping
    public ResponseEntity<RehearsalDto> createRehearsal(@RequestBody RehearsalDto rehearsalDto)
    {
        RehearsalDto createdRehearsal = rehearsalService.createRehearsal(rehearsalDto);
        return new ResponseEntity<>(createdRehearsal, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRehearsal(@PathVariable Long id)
    {
        rehearsalService.deleteRehearsal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
