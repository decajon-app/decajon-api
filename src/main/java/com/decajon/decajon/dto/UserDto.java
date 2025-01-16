package com.decajon.decajon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto
{
    @NotBlank(message = "El nombre no debe estar vacío.")
    private String name;

    @Email(message = "El correo electrónico no es válido.")
    @NotBlank(message = "El correo electrónico no debe estar vacío.")
    private String email;

    @NotBlank(message = "La contraseña no debe estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;
}
