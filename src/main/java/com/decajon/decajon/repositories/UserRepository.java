package com.decajon.decajon.repositories;

import com.decajon.decajon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    // Springboot ya tiene los métodos por defecto.
    // Si es necesario añadir métodos personalizados, agregarlos debajo de esta línea.
}
