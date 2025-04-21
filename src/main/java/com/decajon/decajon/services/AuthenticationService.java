package com.decajon.decajon.services;

import com.decajon.decajon.dto.LoginRequestDto;
import com.decajon.decajon.dto.LoginResponseDto;
import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.UserRepository;
import com.decajon.decajon.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;;
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 31;
    /* Explicacion de los valores para la expiracion de los tokens:
     * 1000 * 60 * 60 * 24 * 7
     * 1s * 60 = 1 min
     * 1min * 60 = 1h
     * 1h * 24 = 1d
     * 1d * 7 = 7d
     */


    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        final String userEmail = loginRequestDto.getEmail();

        // Recuperar la informacion del usuario
        Optional<User> loggedInUser = userRepository.findByEmail(userEmail);
        if(loggedInUser.isEmpty()) {
            throw new RuntimeException("El usuario con el email " + userEmail + " no existe.");
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userEmail,
                loginRequestDto.getPassword()
            )
        );

        String accessToken = jwtUtil.generateToken(userEmail, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = jwtUtil.generateToken(userEmail, REFRESH_TOKEN_EXPIRATION);

        // Guardar el refresh token en la bd
        userRepository.updateRefreshToken(loginRequestDto.getEmail(), refreshToken);

        return new LoginResponseDto(
                accessToken,
                loggedInUser.get().getId(),
                loggedInUser.get().getEmail(),
                loggedInUser.get().getFirstName(),
                loggedInUser.get().getLastName()
        );
    }
}
