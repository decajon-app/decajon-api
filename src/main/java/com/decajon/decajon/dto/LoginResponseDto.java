package com.decajon.decajon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto
{
    private String token;
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
