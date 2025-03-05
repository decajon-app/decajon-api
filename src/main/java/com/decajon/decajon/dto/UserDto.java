package com.decajon.decajon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto
{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
