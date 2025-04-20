package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateGroupDto
{
    @NotBlank(message = "El grupo debe tener un nombre obligatorio")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull(message = "No se puede crear un grupo sin un propietario del grupo.")
    private Long ownerId;
}
