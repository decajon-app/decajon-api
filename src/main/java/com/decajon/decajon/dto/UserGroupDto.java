package com.decajon.decajon.dto;

import lombok.*;

@AllArgsConstructor
@Getter
public class UserGroupDto
{
    private Long userId;
    private Long groupId;
    private String role;
}
