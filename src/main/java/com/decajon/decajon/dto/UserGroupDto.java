package com.decajon.decajon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
public class UserGroupDto
{
    @NotNull
    private Long userId;

    @NotNull
    private Long groupId;

    @NotBlank
    private String password;

    private String role;
}
