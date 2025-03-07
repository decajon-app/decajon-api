package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.LoginRequestDto;
import com.decajon.decajon.dto.LoginResponseDto;
import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.dto.UserRequestDto;
import com.decajon.decajon.services.AuthenticationService;
import com.decajon.decajon.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRequestDto userRequestDto)
    {
        UserDto savedUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        LoginResponseDto response = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
}
