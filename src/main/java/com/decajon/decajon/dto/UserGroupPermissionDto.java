package com.decajon.decajon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupPermissionDto
{
    private Long userId;
    private Long groupId;
    private String role;
}
