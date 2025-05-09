package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class UserDto
{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
