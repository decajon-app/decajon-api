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
@RequestMapping("/api/fsrs/tasks") // Un prefijo específico para las tareas FSRS
public class FSRSTaskController {

    private final FSRSService fsrsService; // Inyecta el servicio FSRS

    // Endpoint para activar manualmente la verificación de tarjetas pendientes (para pruebas)
    @GetMapping("/due-cards/{userId}")
    public ResponseEntity<String> triggerDueCardCheck(@PathVariable Long userId) {
        fsrsService.processDueCards(userId); // Asume que modificaste el método para recibir userId
        return ResponseEntity.ok("Verificación de tarjetas pendientes iniciada.");
    }

    // Endpoint para obtener recomendaciones (podría estar aquí o en otro Controller)
    @GetMapping("/recomendaciones/{userId}")
    public ResponseEntity<List<Integer>> getRecomendaciones(@PathVariable Long userId) {
        List<Integer> recomendaciones = fsrsService.obtenerRecomendaciones(userId);
        return ResponseEntity.ok(recomendaciones);
    }
}