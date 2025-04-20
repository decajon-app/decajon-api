package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.*;
import com.decajon.decajon.models.User;
import com.decajon.decajon.security.JwtUtil;
import com.decajon.decajon.services.AuthenticationService;
import com.decajon.decajon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 días
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 31; // 31 días
    /* Explicacion de los valores para la expiracion de los tokens:
     * 1000 * 60 * 60 * 24 * 7
     * 1s * 60 = 1 min
     * 1min * 60 = 1h
     * 1h * 24 = 1d
     * 1d * 7 = 7d
     */

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDto userRequestDto)
    {
        UserDto savedUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto)
    {
        LoginResponseDto response = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody TokenRequestDto request) throws AccessDeniedException
    {
        String refreshToken = request.getRefreshToken();
        if(!jwtUtil.validateToken(refreshToken))
        {
            return ResponseEntity.status(401).body("Refresh token no valido");
        }

        String email = jwtUtil.extractEmail(refreshToken);
        Optional<User> user = userService.getUserByEmail(email);

        if(user.isPresent() && refreshToken.equals(user.get().getRefreshToken()))
        {
            String newAccessToken = jwtUtil.generateToken(email, ACCESS_TOKEN_EXPIRATION);
            String newRefreshToken = jwtUtil.generateToken(email, REFRESH_TOKEN_EXPIRATION);

            userService.updateRefreshToken(email, newRefreshToken);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("accessToken", newAccessToken));
        }

        return ResponseEntity.status(401).body("Refresh token no valido");
    }
}
