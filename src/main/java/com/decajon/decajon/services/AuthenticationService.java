package com.decajon.decajon.services;

import com.decajon.decajon.dto.LoginRequestDto;
import com.decajon.decajon.dto.LoginResponseDto;
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

    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
            )
        );

        String token = JwtUtil.generateToken(loginRequestDto.getEmail());

        return new LoginResponseDto(token);
    }
}
