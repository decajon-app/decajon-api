package com.decajon.decajon.services;

import com.decajon.decajon.models.Card; // Asumo que tienes una entidad Card
import com.decajon.decajon.repositories.CardRepository; // Asumo que tienes un CardRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class FSRSService {

    @Autowired
    private CardRepository cardRepository;

    @Scheduled(cron = "0 0 * * * ?") // Ejecutar al inicio de cada hora
    public void processDueCards() {
        OffsetDateTime now = OffsetDateTime.now(java.time.ZoneOffset.UTC); // Importante usar OffsetDateTime y UTC
        List<Card> dueCards = cardRepository.findByDueBefore(now); // Método en el repositorio

        if (!dueCards.isEmpty()) {
            System.out.println("Encontradas " + dueCards.size() + " tarjetas para repasar.");
            // Aquí iría la lógica para enviar las tarjetas a FastAPI para obtener el orden de repaso
            // y luego manejar la respuesta (ver más abajo).
            for (Card card : dueCards) {
                System.out.println("Card ID: " + card.getCardId() + ", Due: " + card.getDue());
            }
        } else {
            System.out.println("No hay tarjetas pendientes de repaso.");
        }
    }

    // Método para obtener recomendaciones de repaso desde FastAPI
    // Este método sería llamado por otros servicios o controllers si necesitas acceder a las recomendaciones directamente.
    public List<Integer> obtenerRecomendaciones(Long userId) {
        // Lógica para comunicarse con FastAPI
        // ...
        return null; // Placeholder
    }
}