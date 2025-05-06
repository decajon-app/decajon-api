package com.decajon.decajon.services;

import com.decajon.decajon.models.Card;
import com.decajon.decajon.models.User; // Asumo que tienes una entidad User
import com.decajon.decajon.repositories.CardRepository;
import com.decajon.decajon.repositories.UserRepository; // Asumo que tienes un UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class FSRSService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository; // Inyecta el UserRepository

    @Scheduled(cron = "0 0 12 * * ?") // Ejecutar al inicio de cada hora
    public void processDueCardsForAllUsers() {
        List<User> allUsers = userRepository.findAll(); // Obtener todos los usuarios

        for (User user : allUsers) {
            processDueCardsForUser(user.getId());
        }
    }

    public void processDueCardsForUser(Long userId) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        List<Card> dueCards = cardRepository.findByUserIdAndDueBefore(userId, now);

        if (!dueCards.isEmpty()) {
            System.out.println("Encontradas " + dueCards.size() + " tarjetas pendientes para el usuario " + userId);
            // Aquí iría la lógica para enviar las tarjetas a FastAPI para el usuario específico
            // utilizando el userId en la URL del endpoint de FastAPI.
            // Ejemplo de URL: http://tu_servicio_fastapi:8000/fsrs/recomendaciones/{userId}
            // ...
        } else {
            System.out.println("No hay tarjetas pendientes para el usuario " + userId);
        }
    }

    public List<Integer> getFSRSRecomendations(Long userId) {
        // Lógica para comunicarse con FastAPI para obtener recomendaciones para un usuario
        // ...
        return null; // Placeholder
    }
}