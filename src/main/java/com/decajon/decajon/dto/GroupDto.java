package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupDto
{
    @NotBlank(message = "El campo {id} del grupo se encuentra vacío.")
    private Integer id;

    @NotNull(message = "El campo {ownerId} del grupo se encuentra vacío.")
    private Integer ownerId;

    @NotNull(message = "El campo {name} del grupo se encuentra vacío.")
    private String name;

    @NotNull(message = "El campo {password} del grupo se encuentra vacío.")
    private String password;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
