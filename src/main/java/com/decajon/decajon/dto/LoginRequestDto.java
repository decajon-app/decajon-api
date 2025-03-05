package com.decajon.decajon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto
{
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
