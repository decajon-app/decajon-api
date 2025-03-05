package com.decajon.decajon.services;

import com.decajon.decajon.dto.LoginRequestDto;
import com.decajon.decajon.dto.LoginResponseDto;
import com.decajon.decajon.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService
{
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        System.out.println("TEST 1");
        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  loginRequestDto.getEmail(),
                  loginRequestDto.getPassword()
          )
        );
        System.out.println("TEST 2");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = ((UserDetails) authentication.getPrincipal()).toString();
        System.out.println(email);
        String token = jwtUtil.generateToken(email);

        return new LoginResponseDto(token);
    }
}
