package com.decajon.decajon.repositories;

import com.decajon.decajon.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Metodo para actualizar el token del usuario autenticado
     * @param email
     * @param refreshToken
     */
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.refreshToken = :refreshToken WHERE u.email = :email")
    void updateRefreshToken(@Param("email") String email, @Param("refreshToken") String refreshToken);
}
