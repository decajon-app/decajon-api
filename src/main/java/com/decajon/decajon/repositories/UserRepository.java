package com.decajon.decajon.repositories;

import com.decajon.decajon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    /**
     * Este método es necesario para la autenticación
     * @param email
     * @return User
     */
    Optional<User> findByEmail(String email);
}
