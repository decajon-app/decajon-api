package com.decajon.decajon.services;

import com.decajon.decajon.dto.LoginRequestDto;
import com.decajon.decajon.dto.LoginResponseDto;
import com.decajon.decajon.repositories.UserRepository;
import com.decajon.decajon.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 5;
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;
    /* Explicacion de los valores para la expiracion de los tokens:
     * 1000 * 60 * 60 * 24 * 7
     * 1s * 60 = 1 min
     * 1min * 60 = 1h
     * 1h * 24 = 1d
     * 1d * 7 = 7d
     */


    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
            )
        );

        String accessToken = jwtUtil.generateToken(loginRequestDto.getEmail(), ACCESS_TOKEN_EXPIRATION);
        String refreshToken = jwtUtil.generateToken(loginRequestDto.getEmail(), REFRESH_TOKEN_EXPIRATION);

        userRepository.updateRefreshToken(loginRequestDto.getEmail(), refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
