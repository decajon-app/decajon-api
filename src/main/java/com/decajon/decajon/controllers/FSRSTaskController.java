package com.decajon.decajon.controllers;

import com.decajon.decajon.services.FSRSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fsrs/tasks") 
public class FSRSTaskController {

    private final FSRSService fsrsService; 

    
    @GetMapping("/due-cards/{userId}")
    public ResponseEntity<String> triggerDueCardCheck(@PathVariable Long userId) {
        fsrsService.processDueCardsForUser(userId); 
        return ResponseEntity.ok("Verificación de tarjetas pendientes iniciada.");
    }


    @GetMapping("/recomendaciones/{userId}")
    public ResponseEntity<List<Integer>> getFSRSService(@PathVariable Long userId) {
        List<Integer> recomendaciones = fsrsService.getFSRSRecomendations(userId);
        return ResponseEntity.ok(recomendaciones);
    }
}