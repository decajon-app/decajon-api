package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto
{
    private Integer id;

    @NotNull(message = "No se puede crear un grupo sin un propietario del grupo.")
    private Integer ownerId;

    @NotNull(message = "El grupo debe tener un nombre obligatorio")
    private String name;

    @NotNull(message = "Es obligatorio tener una contraseña para crear el grupo.")
    @Size(min = 8)
    private String password;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
